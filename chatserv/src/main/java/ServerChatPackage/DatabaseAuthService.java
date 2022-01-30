package ServerChatPackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseAuthService implements AuthService {


    private static Connection conn;
    private static Statement stmt;

    @Override
    public void start() {
        try {
            conn = DBConnection.getConn();
            stmt = conn.createStatement();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Сервис аутентификации запущен");
    }

    @Override
    public void stop() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Сервис аутентификации остановлен");
    }

    //Таблица на sqlite создавалась кодом ниже:
    /*    CREATE TABLE CHAT_USERS (
        USER_ID       INTEGER       UNIQUE
        NOT NULL
        PRIMARY KEY AUTOINCREMENT,
        NICKNAME      VARCHAR (256) UNIQUE
        NOT NULL,
        LOGIN         VARCHAR (256) UNIQUE
        NOT NULL,
        PASSWORD_HASH INTEGER       NOT NULL
    );
    INSERT INTO CHAT_USERS
    (
        NICKNAME
        ,LOGIN
        ,PASSWORD_HASH
    )
    VALUES
    (
        'nick1'
        ,'login1'
        ,106438208
    ),
    (
        'nick2'
        ,'login2'
        ,106438209
    ),
    (
        'nick3'
        ,'login3'
        ,106438210
    );
    повторно его запускать смысла не вижу*/

    public DatabaseAuthService() {

    }

    @Override
    public String getNickByLoginPass(String login, String pass) {
        String nick = null;
        try (ResultSet rs = stmt.executeQuery("SELECT nickname FROM CHAT_USERS WHERE LOGIN = '" + login +
                "' AND PASSWORD_HASH = " + pass.hashCode() + ";")) {
            rs.next();
            nick = rs.getString("nickname");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nick;
    }
    public boolean changeNick(String nickOld, String nickNew){
        try {
            stmt.executeUpdate("UPDATE CHAT_USERS SET NICKNAME = '"+ nickNew + "' WHERE NICKNAME = '"+ nickOld + "';");
        } catch (SQLException e) {
            return false;   //Изменение никнейма не удалось
        }
        return true;
    }

}