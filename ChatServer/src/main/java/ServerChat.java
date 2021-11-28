import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ServerChat {

    private static final Scanner SC = new Scanner(System.in);

    //Делаем поток для чтения и переотправки сообщений пользователя
    public static class ThreadClientListener implements Runnable {

        private static DataInputStream in;
        private static DataOutputStream out;
        private static Socket socket;

        public ThreadClientListener(Socket socket, DataInputStream in, DataOutputStream out) {
            this.in = in;
            this.out = out;
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String str;
                    str = in.readUTF();
                    System.out.println("Клиент: " + str);//сначала выводим в серверную консоль то, что прислал нам клиент
                    //системная команда /end прекращает работу сервера
                    if (str.equalsIgnoreCase("/end")) {
                        out.writeUTF("Отключение сервера по клиентской команде"); //Оповещаем об отключение сервера
                        kickClient(out); //Перед выключением сервера выкинем всех клиентов с него (пока что одного)
                        System.exit(100); //пусть 100 - код выхода по просьбе со стороны клиента
                    } else if (str.equalsIgnoreCase("/esc")) {
                        kickClient(out);
                        //если клиент послал нам системную команду /esc, то прекращаем с ним связь
                        break;
                    } else {
                        out.writeUTF("Вы: " + str); //Отправляем сообщение обратно в клиентский чат
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void serverInputListener(DataOutputStream out) {
        //Объявляем переменную входящей строки до цикла чтения с потока, чтобы можно было распознать команду выхода
        String str = null; //Строка команд из консоли сервера
        //А пока этот поток работает, мы можем сами общаться с клиентом, благодаря циклу ниже
        while (true) {  //внутренний цикл чтения команд из консоли сервера
            if(ServerApi.socket.isClosed()){
                break;  //Если сокет закрыт, то значит нам больше некому сейчас писать - прекращаем цикл чтения консоли
            }
            if (SC.hasNextLine()) {
                str = SC.nextLine();

                if (str.equalsIgnoreCase("/end")) {
                    kickClient( out); //Перед выключением сервера выкинем всех клиентов с него (пока что одного)
                    System.exit(10); //10 - код выхода по просьбе со стороны СЕРВЕРА
                }
                if (str.equalsIgnoreCase("/kick")) {
                    kickClient(out);
                    break; //Выходя из этого цикла чтения консоли, мы возвращаемся в метод main и ждем нового соединения
                }
            }
            try {
                out.writeUTF("Сервер: " + str); //Отправляем сообщение в клиентский чат
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean kickClient(DataOutputStream out) {
        try {
            out.writeUTF("/kick"); //Отправляем сообщение обратно в клиентский чат, чтобы клиент обработал выход
            ServerApi.socket.close();
            return true;    //получилось отключить клиента от сервера
        } catch (IOException e) {
            e.printStackTrace();
            return false;   //Не получилось отключить клиента от сервера
        }
    }

}
