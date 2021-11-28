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
        разобраться с кодом с занятия, он является фундаментом проекта-чата
        ВАЖНО! Сервер общается только с одним клиентом, т.е. не нужно запускать цикл, который будет ожидать
        второго/третьего/n-го клиентов*/

public class ServerApi {

    static Socket socket = null;
    static DataInputStream in;
    static DataOutputStream out;


    public static void main(String[] args) {

        while (true) {  //Внешний цикл постоянной работы сервера. Если соединение прервано, мы готовы наладить новое
            //откроем соединение и подготовим сокет
            try (ServerSocket serverSocket = new ServerSocket(8181)) {
                System.out.println("Сервер запущен, ожидаем подключения...");
                socket = serverSocket.accept();

                System.out.println("Клиент подключился к вашему серверу сетевого чата! " + '\n');
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());

                Thread threadReader = new Thread(new ServerChat.ThreadClientListener(socket, in, out));
                //Запускаем задачу-поток, занимающийся чтением сокета связи с клиентом и ответами ему
                threadReader.start();
                //переходим в режим ввода сообщений с консоли сервера
                ServerChat.serverInputListener(out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
