package ServerChatPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection conn;

    private DBConnection(){
    }

    public static Connection getConn() throws SQLException {
        if(DBConnection.conn == null){

            conn = DriverManager.getConnection("jdbc:sqlite:chat.db");
        }
        return DBConnection.conn;
    }
}
