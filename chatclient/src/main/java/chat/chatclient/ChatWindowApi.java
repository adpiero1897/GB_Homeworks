package chat.chatclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

/* По сравнению с версией из ДЗ №7 добавлены возможности:
     1. При захождении/выходе из чата клиентов оповещаются автоматически весь список присутствующих в этом чате.
     2. Все системные команды от клиента (и от сервера тоже) теперь находятся в едином блоке switch/case.
     3. Клиент теперь реагирует на внесистемные/некорректные отключение сервера (как и сервер реагировал на отключения клиента).
     4. Клиенту сообщается при авторизации ещё и тот ник, под которым он авторизовался (или пытался авторизоваться).
     5. Реализовано отключение неавторизованных пользователей по времени (120 сек).
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
        stage.show();

    }


    public static void main(String[] args) {
        launch();
    }


}