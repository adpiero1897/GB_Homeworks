module com.example.chatclient {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.chatclient to javafx.fxml;
    exports com.example.chatclient;
}