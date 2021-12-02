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
            new Thread(() -> {
                try {
                    authorization();
                    readMessages();
                } catch (IOException e) {
                    //e.printStackTrace();можно не выводить, т.к. при закрытии in потока, цикл продолжает читать - норма
                } finally {
                    closeConnection();
                }
            }).start();
        } catch (IOException e) {
            throw new RuntimeException("Проблемы при создании обработчика клиента");
        }
    }

    public void authorization() throws IOException {
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
                    sendMessageToClient("/autherror");
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
            if (strFromClient.equals("/esc")) {
                sendMessageToClient("/kick");
                myServer.unsubscribe(this);
                return;
            } else if (strFromClient.equalsIgnoreCase("/end")) {
                myServer.broadcastMsg("Отключение сервера по команде клиента " + name);
                //Оповещаем об отключение сервера
                myServer.broadcastMsg("/kick"); //Перед выключением сервера выкинем всех клиентов с него (пока что одного)
                System.exit(100); //пусть 100 - код выхода по просьбе со стороны клиента
            } else {    //Если ни одно из этих служебных, то просто рассылаем это сообщение клиента
                if (strFromClient.startsWith("/w ")) {    //Если клиент послал личное сообщение кому-то
                    String[] parts = strFromClient.split("\\s+");
                    if(parts.length > 2) {  //иначе пустое сообщение отправлять не будем
                        myServer.broadcastMsg(strFromClient.substring(parts[1].length()+4), this.getName(), parts[1]);
                        //перегрузка метода для отправки личного сообщения, начало которого (указание адресата) отрезаем
                    }
                } else {    //Если на всех в чате сообщение от клиента
                    myServer.broadcastMsg(name + ": " + strFromClient);
                }
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
