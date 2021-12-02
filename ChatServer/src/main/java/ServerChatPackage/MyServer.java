package ServerChatPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MyServer {

    private final int PORT = 8181;
    private static final Scanner SC = new Scanner(System.in);
    private Thread inputServerThread;

    private AuthService authService;
    private List<ClientHandler> clients;

    public MyServer() {

        //Чтобы сервер участвовал в чате и контролировал его, создадим поток, чтения ввода из консоли самого сервера
        inputServerThread = new Thread(() -> {
            //А пока этот поток работает, мы можем сами общаться с клиентом, благодаря циклу ниже
            while (true) {  //Внутренний цикл чтения команд из консоли сервера.
                //Объявляем переменную входящей строки до цикла чтения с потока
                String str; //Строка команд из консоли сервера
                try {
                    if (SC.hasNextLine()) {
                        str = SC.nextLine();
                        if (!str.trim().isEmpty()) {    //на пустой ввод в консоли никак не реагируем
                            String[] parts = str.split("\\s+");
                            if (str.equalsIgnoreCase("/end")) {
                                authService.stop();
                                this.broadcastMsg("*** ОБЩЕЕ ОТКЛЮЧЕНИЕ СЕРВЕРА"); //Перед выключением сервера выкинем всех клиентов с него
                                this.broadcastMsg("/kick"); //Перед выключением сервера выкинем всех клиентов с него
                                System.exit(10); //10 - код выхода по просьбе со стороны СЕРВЕРА
                            } else if (parts[0].equalsIgnoreCase("/kick")) {
                                //реакция на команду /kick от сервера (/kick имя_клиента_чата) - изгнание
                                for (ClientHandler o : clients) {   //находим клиента на вылет в списке
                                    if (o.getName().equals(parts[1])) {   //Нашли того пользователя, которого нужно выгнать
                                        o.sendMessageToClient("/kick"); //пошлем команду на вылет именно тому клиенту
                                        this.unsubscribe(o);
                                        break;
                                    }
                                }
                                this.broadcastMsg("*** " + parts[1] + " был выгнан из чата");
                            } else {
                                this.broadcastMsg("*сервер*: " + str);
                            }
                        }
                    }
                } catch (IndexOutOfBoundsException exception) {
                    System.out.println("ошибка сканера");
                }
            }
        });
        inputServerThread.start();

        try (ServerSocket server = new ServerSocket(PORT)) {
            authService = new BaseAuthService();
            authService.start();
            clients = new ArrayList<>();
            while (true) {  //зацикливаем основной поток серверной программы на принятие подключений от сокетов клиентов
                System.out.println("Сервер ожидает подключения...");
                Socket socket = server.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            System.out.println("Ошибка в работе сервера");
        } finally {
            if (authService != null) {
                authService.stop();
            }
        }
    }

    public synchronized boolean isNickBusy(String nick) {
        for (ClientHandler o : clients) {
            if (o.getName().equals(nick)) {
                return true;
            }
        }
        return false;
    }

    //метод рассылки сообщение из чата на всех
    public synchronized void broadcastMsg(String msg) {
        for (ClientHandler acceptor : clients) {
            acceptor.sendMessageToClient(msg);
        }
    }

    //перегрузка метода рассылки для Личных сообщений
    public synchronized void broadcastMsg(String msg, String nickSender, String nickAcceptor) {
        if(nickSender.equals(nickAcceptor)) {//Если сообщение послано самому себе было от клиента
            for (ClientHandler sender : clients) {
                if (sender.getName().equals(nickSender)) {
                    sender.sendMessageToClient("Иногда бывает полезно наладить диалог с самим собой");
                    return;     //Не будем далее отсылать пользовательское сообщение самому себе
                }
            }
        }
        for (ClientHandler acceptor : clients) {
            if (acceptor.getName().equals(nickAcceptor)) {   //Нашли, кому адресовано сообщение?
                acceptor.sendMessageToClient("Личное сообщение вам от " + nickSender + " : " + msg);
                //отослали личное сообщение адресату, теперь отпишемся об этом отправителю того сообщения
                for (ClientHandler sender : clients) {
                    if (sender.getName().equals(nickSender)) {
                        sender.sendMessageToClient("Вы лично для " + nickAcceptor + " : " + msg);
                    }
                }
                return; //Сообщение доставлено: далее делать ничего не нужно с отправкой
            }
        }
        //Пошлем отправителю сообщение, что указанного им адресата в чате не нашли
        for (ClientHandler sender : clients) {
            if (sender.getName().equals(nickSender)) {
                sender.sendMessageToClient("Адресат " + nickAcceptor + " не найден!");
            }
        }
    }

    public synchronized void unsubscribe(ClientHandler o) {
        clients.remove(o);
    }

    public synchronized void subscribe(ClientHandler o) {
        clients.add(o);
    }


    public AuthService getAuthService() {
        return authService;
    }

}
