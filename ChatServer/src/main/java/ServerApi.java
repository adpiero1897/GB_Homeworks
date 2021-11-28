import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/*1. Написать консольный вариант клиент\серверного приложения, в котором пользователь может писать сообщения, как на
клиентской стороне, так и на серверной. Т.е. если на клиентской стороне написать "Привет", нажать Enter то сообщение
 должно передаться на сервер и там отпечататься в консоли. Если сделать то же самое на серверной стороне, сообщение
  соответственно передается клиенту и печатается у него в консоли. Есть одна особенность, которую нужно учитывать:
   клиент или сервер может написать несколько сообщений подряд, такую ситуацию необходимо корректно обработать
        Разобраться с кодом с занятия, он является фундаментом проекта-чата
        ВАЖНО! Сервер общается только с одним клиентом, т.е. не нужно запускать цикл, который будет ожидать
        второго/третьего/n-го клиентов*/

public class ServerApi {

    static Socket socket = null;
    static DataInputStream in;
    static DataOutputStream out;

    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        //Объявляем перемнную входящей строки до цикла чтения с потока, чтобы можно было распознать команду выхода
        String str=""; //Строка команд из консоли сервера
        Thread threadReader = new Thread(new ThreadClientListener());
        //откроем соединение и подготовим сокет
        try (ServerSocket serverSocket = new ServerSocket(8181)) {
            System.out.println("Сервер запущен, ожидаем подключения...");
            socket = serverSocket.accept();
            System.out.println("Успешно установлено подключение к серверу сетевого чата! " + '\n');
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            //Запускаем задачу-поток, занимающийся чтением сокета связи с клиентом и ответами ему
            threadReader.start();

            //А пока этот поток работает, мы можем сами общаться с клиентом, благодаря циклу ниже
            while (true) {  //внутренний цикл чтения команд из консоли сервера
                if (SC.hasNextLine()) {
                    str = SC.nextLine();

                    if (str.equals("/end")) {
                        System.exit(100); //пусть 100 - код выхода по просьбе со строны клиента
                        //break;
                    }
                }
                out.writeUTF("Сервер: " + str); //Отправляем сообщение в клиентский чат
                //SC.nextLine();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Делаем поток для чтения и переотправки сообщений пользователя
    public static class ThreadClientListener implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    String str;
                    str = in.readUTF();
                    System.out.println("Клиент: " +str);//сначала выводим в серверную консоль то, что прислал нам клиент
                    //системная команда /end прекращает рабоут сервера
                    if(str.equals("/end")){
                        out.writeUTF("Отключение сервера по клиентской команде"); //Оповещаем об отключение сервера
                        System.exit(100); //пусть 100 - код выхода по просьбе со строны клиента
                    }
                    if (str.equals("/esc")) {
                        break;      //если клиент послал системную команду /esc, то прекращаем с ним связь
                    }
                    out.writeUTF("Вы: " + str); //Отправляем сообщение обратно в клиентский чат
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
