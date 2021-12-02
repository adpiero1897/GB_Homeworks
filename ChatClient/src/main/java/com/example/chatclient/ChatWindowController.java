package com.example.chatclient;

import javafx.application.Platform;
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
    private final Object monitor = new Object();

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
            SendText.setText("");
        }
    }

    @FXML
    protected void onConnectClick() {
        if (!isConnect) {
            chatClient = new ChatClient();  //Пробуем открыть соединение и потоки с сервером Сетевого чата
        } else {    //Если уже есть открытое соединение с чатом
            ConnectButton.setText("Подсоединиться к серверу");
            chatClient.sendMessage("/esc");//Оповещаем сервер о том, что мы дисконнектимся
        }
    }

    //Обработчик выхода из окна сделаем, чтобы предварительно закрывать соединение с сервером (оповестить его о выходе)
    private javafx.event.EventHandler<WindowEvent> closeEventHandler = event -> {
        //чтобы разлогиниться с сервера (доработка) перед закрытием приложения нашего
        synchronized (monitor) {    //Синхронизируем задачу, чтобы она ждала, пока сервер отреагирует на наш выход
            chatClient.sendMessage("/esc");//Оповещаем сервер о том, что мы выходим: теперь ему можно перестать слушать наш сокет
            try {
                if (!chatClient.socket.isClosed()) { //ждем, если коннект сокета не закрыт
                    monitor.wait(2000); //ждем максимум 2 секунды
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                Platform.exit();    //в любом случае по нажатию "крестика" окно приложения закрывается
            }
        }
    };

    //Геттер для контроллера закрытия в main класс нашего приложения
    public javafx.event.EventHandler<WindowEvent> getCloseEventHandler() {
        return closeEventHandler;
    }

    //Внутренний подкласс для коннекта и передачи сообщений с сетевым чатом
    public class ChatClient {
        private final String SERVER_ADDR = "localhost";
        private final int SERVER_PORT = 8181;

        private Socket socket;
        private DataInputStream in;
        private DataOutputStream out;
        private int authStep;   //Чтобы понимать, если мы находимся на каком-либо шаге авторизации

        public ChatClient() {
            try {
                openConnection();
            } catch (IOException e) {
                chatTextArea.appendText("Не удалось подсоединиться к серверу" + "\n");
            }
        }

        public void openConnection() throws IOException {
            socket = new Socket(SERVER_ADDR, SERVER_PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            isConnect = true;
            chatTextArea.appendText("Вы соединились с сервером чата. Нужно авторизоваться." + '\n'
                    + "Введите логин" + '\n');
            ConnectButton.setText("Отсоединиться");
            SendText.setVisible(true);
            SendText.setPromptText("Введите логин");
            authStep = 1;
            SendButton.setVisible(true);
            new Thread(() -> {
                try {
                    while (true) {
                        String serverMessage = in.readUTF();
                        if (serverMessage.equalsIgnoreCase("/kick")) {
                            closeConnection(); //Если с сервера прилетает команда /kick, то соединение с ним закрывается
                            break;
                        }
                        if (serverMessage.startsWith("/authok")) { //если ответ сервера: успешная авторизация
                            authStep = 0;
                            chatTextArea.appendText("Вы успешно авторизовались в чате" + '\n');
                            SendText.setPromptText("Введите текст вашего сообщения ");
                        } else if(serverMessage.startsWith("/autherror")){
                            authStep = 1;   //возвращаемся на шаг ввода логина, если ошибка в логине/пароле нашем
                            chatTextArea.appendText("Снова введите логин" + '\n');
                            SendText.setPromptText("Введите логин");
                        }
                        else {
                            chatTextArea.appendText(serverMessage + "\n");
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

        }

        public void closeConnection() {
            synchronized (monitor) {    //Синхронизируем по монитору, чтобы обеспечить синхронность ивента закрытия нашего окна
                try {
                    in.close();
                    out.close();
                    socket.close();
                    isConnect = false;
                    chatTextArea.appendText("Вы отключились от сервера сетевого чата! " + '\n');
                    ConnectButton.setText("Подсоединиться к серверу");
                    SendText.setVisible(false);
                    SendButton.setVisible(false);
                    monitor.notify();   //оповестили ивент закрытия окна, что сейчас можно выключать это приложение
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        public void sendMessage(String message) {
            try {
                out.writeUTF(message);
                if (authStep == 1) {  //Если мы вводим пока ещё только свой логин в рамках авторизации
                    chatTextArea.appendText("Теперь введите свой пароль" + '\n');
                    SendText.setPromptText("Введите свой пароль");
                    authStep = 2;
                }
            } catch (IOException e) {
                e.printStackTrace();
                chatTextArea.appendText("Ошибка отправки сообщения" + '\n');
            }
        }

    }

}