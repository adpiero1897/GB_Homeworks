import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerChat {

    private static final Object monitor = new Object();
    private static final Scanner SC = new Scanner(System.in);
    private static Thread inputServerThread;
    private static Socket socket;
    private static DataInputStream in;
    private static DataOutputStream out;


    //Делаем поток для чтения и переотправки сообщений пользователя
    public static class ThreadClientListener implements Runnable {

        @Override
        public void run() {
            try {
                while (!socket.isClosed()) {    //Проверяем, открыт ли сокет

                    String str;
                    str = in.readUTF();
                    //системная команда /end прекращает работу сервера
                    if (str.equalsIgnoreCase("/end")) {
                        out.writeUTF("Отключение сервера по клиентской команде"); //Оповещаем об отключение сервера
                        kickClient(); //Перед выключением сервера выкинем всех клиентов с него (пока что одного)
                        System.exit(100); //пусть 100 - код выхода по просьбе со стороны клиента
                    } else if (str.equalsIgnoreCase("/esc")) {
                        kickClient();
                        //если клиент послал нам системную команду /esc, то прекращаем с ним связь
                        break;
                    } else {
                        System.out.println("Клиент: " + str);//сначала выводим в серверную консоль то, что прислал нам клиент
                        out.writeUTF("Вы: " + str); //Отправляем сообщение обратно в клиентский чат
                    }

                }
            } catch (IOException e) {
                if(!socket.isClosed()) {    //Если при этом сокет не был закрыт, то исключение нужно вывести иначе
                    e.printStackTrace();    //просто завершаем задачу считывания
                }
            }
        }
    }

    public static void acceptClientConnection(ServerSocket socketServer) {
        synchronized (monitor) {
            try {
                System.out.println("Сервер запущен, ожидаем подключения...");

                socket = socketServer.accept();

                System.out.println("Клиент подключился к вашему серверу сетевого чата! " + '\n');
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());

                Thread threadReader = new Thread(new ServerChat.ThreadClientListener());
                //Запускаем задачу-поток, занимающийся чтением сокета связи с клиентом и ответами ему
                threadReader.start();
                monitor.wait();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static void serverInputListener() {
        inputServerThread = new Thread(() -> {

            //А пока этот поток работает, мы можем сами общаться с клиентом, благодаря циклу ниже
            while (true) {  //Внутренний цикл чтения команд из консоли сервера.
                //Объявляем переменную входящей строки до цикла чтения с потока
                String str = null; //Строка команд из консоли сервера
                try {
                    if (SC.hasNextLine()) {
                        str = SC.nextLine();
                        if (!str.trim().isEmpty()) {    //на пустой ввод в консоле никак не реагируем

                            if (str.equalsIgnoreCase("/end")) {
                                if (socket != null && !socket.isClosed()) {    //Если у нас есть клиенты на сервере
                                    kickClient(); //Перед выключением сервера выкинем
                                }
                                // всех клиентов с него (пока что одного)
                                System.exit(10); //10 - код выхода по просьбе со стороны СЕРВЕРА
                            }
                            if (str.equalsIgnoreCase("/kick")) {
                                kickClient();
                            }
                        }

                        out.writeUTF("Сервер: " + str); //Отправляем сообщение в клиентский чат
                    }
                } catch (IOException e) {
                    System.out.println("Некому передавать ваше сообщение. Вас никто не услышит");
                } catch (IndexOutOfBoundsException exception) {
                    System.out.println("ошибка сканера");
                    //break;  //Прерываем цикл считывания, чтобы после разрыва соединения запустился новый сканер
                }
            }
        });
        inputServerThread.start();

    }

    public static boolean kickClient() {
        synchronized (monitor) {
            try {
                out.writeUTF("/kick"); //Отправляем сообщение обратно в клиентский чат, чтобы клиент обработал выход
                socket.close();
                monitor.notify();
                //Оповестим main-метод класса ServerApi, что старый сокет закрыли, и нужно снова быть готовым к клиенту
                return true;    //получилось отключить клиента от сервера
            } catch (IOException e) {
                e.printStackTrace();
                return false;   //Не получилось отключить клиента от сервера
            }
        }
    }
}
