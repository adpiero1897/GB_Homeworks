import ServerChatPackage.MyServer;


/* . Добавить на серверную сторону чата логирование, с выводом информации о действиях на сервере (запущен, произошла
 ошибка, клиент подключился, клиент прислал сообщение/команду).
 */
public class ServerApi {


    public static void main(String[] args) {
        new MyServer();
    }

}
