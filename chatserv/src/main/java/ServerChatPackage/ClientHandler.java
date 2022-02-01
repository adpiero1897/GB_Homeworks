package ServerChatPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {

    private MyServer myServer;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private String name;

    public String getName() {
        return name;
    }

    public ClientHandler(MyServer myServer, Socket socket) {
        try {
            this.myServer = myServer;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.name = "";

            myServer.getExecutorService().execute(() -> {
                try {
                    authorization();
                    readMessages();
                } catch (IOException e) {
                    //e.printStackTrace();можно не выводить, т.к. при закрытии in потока, цикл продолжает читать - норма
                } finally {
                    closeConnection();
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("Проблемы при создании обработчика клиента");
        }
    }

    public void authorization() throws IOException {
        socket.setSoTimeout(120000); //отключение неавторизованных пользователей по времени (120 сек)
        String login = "";
        int authStep = 1;   //Показывает шаг авторизации клиента (вводит ли он сейчас нам свой логин или пароль)
        while (authStep < 3) {  //пока пользователь не залогинится, цикл повторяется
            String str = in.readUTF();
            if (str.equals("/esc")) {       //Если на этапе авторизации пользователь решит выйти
                sendMessageToClient("/kick");
            }
            if (authStep == 2) {    //Последний этап авторизации - ввод пользовательского пароля
                String nick = myServer.getAuthService().getNickByLoginPass(login, str);
                if (nick != null) {
                    if (!myServer.isNickBusy(nick)) {
                        socket.setSoTimeout(0);    //вернем дефолтное значение для ожидания сообщений клиента
                        sendMessageToClient("/authok " + nick);
                        name = nick;
                        myServer.broadcastMsg(name + " зашел в чат");
                        myServer.subscribe(this);
                        return;
                    } else {
                        sendMessageToClient("Учетная запись уже используется");
                        sendMessageToClient("/autherror " + nick);
                        authStep = 0;
                        login = "";
                    }
                } else {
                    sendMessageToClient("Неверные логин/пароль");
                    sendMessageToClient("/autherror " + login);
                    authStep = 0;
                    login = "";
                }
            } else {    //если шаг авторизации authStep == 1 - Этап ввода пользователем своего логина
                login = str;    //запомнили логин, ждем, пока клиент пришлет нам свой пароль
            }
            authStep++; //переходим к следующему шагу авторизации
        }
    }

    public void readMessages() throws IOException {
        while (true) {
            String strFromClient = in.readUTF();
            System.out.println("от " + name + ": " + strFromClient);
            if (strFromClient.startsWith("/")) {    //блок системных команд
                String[] parts = strFromClient.split("\\s+");

                switch (parts[0].toLowerCase()) {
                    case "/esc":    //Клиент просит нас выкинуть его с сервера
                        sendMessageToClient("/kick");
                        myServer.unsubscribe(this);
                        return;
                    case "/end":
                        myServer.broadcastMsg("Отключение сервера по команде клиента " + name);
                        //Оповещаем об отключение сервера
                        myServer.broadcastMsg("/kick"); //Перед выключением сервера выкинем всех клиентов с него (пока что одного)
                        //Перед стопом серверного приложения, закроем executeservice и соединение с БД авторизации
                        myServer.getExecutorService().shutdown();
                        myServer.getAuthService().stop();
                        System.exit(100); //пусть 100 - код выхода по просьбе со стороны клиента
                        break;
                    case "/w": //Если пользователь пытается послать приватное сообщение другому пользователю
                        if (parts.length > 2) {  //иначе пустое сообщение отправлять не будем
                            myServer.broadcastMsg(strFromClient.substring(parts[1].length() + 4), this.getName(), parts[1]);
                            //перегрузка метода для отправки личного сообщения, начало которого (указание адресата) отрезаем
                        }
                        break;
                    case "/chnick":
                        //Вызываем метод изменения никнейма в базе. Если всё успешно, то он возвращает true.
                        if (parts.length == 2 && myServer.getAuthService().changeNick(this.name,parts[1])) {
                            String nick_old = name;
                            name = parts[1];
                            this.sendMessageToClient("Ваш никнейм был успешно изменен на " + parts[1] + " !");
                            myServer.nickChanged(nick_old,name);   //оповещаем всех клиентов, кто, на какой, ник поменял
                        } else {
                            this.sendMessageToClient("Попытка изменения никнейма не удалась: некорретное новое имя");
                        }
                        break;
                    default:
                        this.sendMessageToClient("Системная команда " + parts[0] + " не распознана");
                }
            } else {    //Если на всех в чате сообщение от клиента
                myServer.broadcastMsg(name + ": " + strFromClient);
            }
        }
    }


    public void sendMessageToClient(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void closeConnection() {
        myServer.unsubscribe(this);
        myServer.broadcastMsg(name + " вышел из чата");
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
