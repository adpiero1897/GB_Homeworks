import ServerChatPackage.MyServer;

/* По сравнению с версией из ДЗ №8_курс_2 добавлены возможности:
1. Добавить в сетевой чат авторизацию через базу данных SQLite.
2.*Добавить в сетевой чат возможность смены ника.
Реализовано по команде /chnick [новое_имя_пользователя]
 */
public class ServerApi {

    public static void main(String[] args) {
        new MyServer();
    }

}
