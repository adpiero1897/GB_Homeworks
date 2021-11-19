package com.example.chat_window_javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


import java.io.FileInputStream;


public class ChatWindowController {

    @FXML
    private TextArea chatTextArea;

    @FXML
    private TextField SendText;

    @FXML
    private VBox VBOX;

    @FXML
    private Button SendButton;

    //ОСНОВНАЯ ЧАСТЬ: При нажатии Enter или кнопки "отправить" перекидываем текст в общее поле TextArea
    @FXML
    protected void onSendClick() {
        //Чтобы нельзя было послать пустое сообщение, проверяем, пустое ли поле ввода
        if (SendText.getText().length() > 0) {
            chatTextArea.setText(chatTextArea.getText() + "Вы: " + SendText.getText() + '\n');
            SendText.setText("");
        }
    }


}