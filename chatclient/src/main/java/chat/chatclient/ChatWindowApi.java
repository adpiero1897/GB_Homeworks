package chat.chatclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

/* По сравнению с версией из ДЗ №2_курс_3 добавлены возможности:
     1. Добавить в сетевой чат запись локальной истории в текстовый файл на клиенте.
     2. После загрузки клиента показывать ему последние 100 строк чата.
 */

public class ChatWindowApi extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ChatWindowApi.class.getResource("chat-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 545, 380);
        stage.setTitle("MyChatName");
        stage.setScene(scene);
        //установим созданный в классе контроллера ивент для закрытия окна нашего приложения
        ChatWindowController controller = fxmlLoader.getController();
        stage.setOnCloseRequest(controller.getCloseEventHandler());
        controller.readChatLog();
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }


}