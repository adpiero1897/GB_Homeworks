package com.example.chatclient;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class ChatWindowController {

    private ChatClient chatClient;  //Объект для коннекта и передачи сообщений с сетевым чатом
    boolean isConnect = false;

    @FXML
    private TextArea chatTextArea;

    @FXML
    private TextField SendText;

    @FXML
    private Button SendButton;

    @FXML
    private Button ConnectButton;

    //ОСНОВНАЯ ЧАСТЬ: При нажатии Enter или кнопки "отправить" перекидываем текст в общее поле TextArea
    @FXML
    protected void onSendClick() {
        //Чтобы нельзя было послать пустое сообщение, проверяем, пустое ли поле ввода
        if (!SendText.getText().trim().isEmpty()) {
            chatClient.sendMessage(SendText.getText());  //передаем в метод отправки это наше сообщение
            //chatTextArea.appendText("Вы: " + SendText.getText() + '\n');
            SendText.setText("");
        }
    }

    @FXML
    protected void onConnectClick() {
        if (!isConnect) {
            chatClient = new ChatClient();  //Пробуем открыть соединение и потоки с сервером Сетевого чата
            //chatTextArea.setText(chatTextArea.getText() + "Успешно установлено подключение к серверу сетевого чата! " + '\n');
        } else {    //Если уже есть открытое соединение с чатом
            chatClient.closeConnection();
            //chatTextArea.setText(chatTextArea.getText() + "Вы отключились от сервера сетевого чата! " + '\n');
        }
    }

    //Обработчик выхода из окна сделаем, чтобы предварительно закрывать соединение с сервером (оповестить его о выходе)
    private javafx.event.EventHandler<WindowEvent> closeEventHandler = event -> {
        chatClient.closeConnection(); //чтобы разлогиниться с сервера (доработка) перед закрытием приложения нашего
        Platform.exit();
    };

    //Геттер для контроллера закрытия в main класс нашего приложения
    public javafx.event.EventHandler<WindowEvent> getCloseEventHandler(){
        return closeEventHandler;
    }

    //Внутренний подкласс для коннекта и передачи сообщений с сетевым чатом
    public class ChatClient {
        private final String SERVER_ADDR = "localhost";
        private final int SERVER_PORT = 8181;

        private Socket socket;
        private DataInputStream in;
        private DataOutputStream out;

        public ChatClient() {
            try {
                openConnection();

                ConnectButton.setText("Отсоединиться");
                SendText.setVisible(true);
                SendButton.setVisible(true);
                //chatTextArea.appendText("Успешно установлено подключение к серверу сетевого чата! " + '\n');
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void openConnection() throws IOException {
            socket = new Socket(SERVER_ADDR, SERVER_PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> {
                try {
                    while (true) {
                        String serverMessage = in.readUTF();
                        if (serverMessage.equalsIgnoreCase("/end")) {
                            break;
                        }
                        chatTextArea.appendText(serverMessage + "\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        public void closeConnection() {
            try {
                sendMessage("/esc");//Оповещаем сервер о том, что мы выходим: теперь ему можно перестать слушать наш сокет
                in.close();
                out.close();
                socket.close();
                isConnect = false;
                //chatTextArea.setText(chatTextArea.getText() + "Вы отключились от сервера сетевого чата! " + '\n');
                chatTextArea.appendText("Вы отключились от сервера сетевого чата! " + '\n');
                ConnectButton.setText("Подсоединиться к серверу");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void sendMessage(String message) {
            try {
                out.writeUTF(message);
            } catch (IOException e) {
                e.printStackTrace();
                chatTextArea.appendText("Ошибка отправки сообщения");
            }
        }


    }

}