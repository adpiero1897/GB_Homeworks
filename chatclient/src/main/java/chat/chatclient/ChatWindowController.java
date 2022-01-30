package chat.chatclient;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class ChatWindowController {

    private ChatClient chatClient;  //Объект для коннекта и передачи сообщений с сетевым чатом
    boolean isConnect = false;
    private final Object monitor = new Object();

    BufferedWriter logWriter;   //объявляем сразу в классе, чтобы не переоткрывать поток кучу раз
    private final int MAX_LOG_COUNT_STRINGS = 100;  //Установим сразу сколько мы последних строк читаем из логов чата

    @FXML
    private TextArea chatTextArea;

    @FXML
    private TextArea clientsTextArea;

    @FXML
    private TextField SendText;

    @FXML
    private Button SendButton;

    @FXML
    private Button ConnectButton;

    //Метод для подгрузки последних 100 строк из логов чата при каждом запуске клиентского приложения
    public void readChatLog() {
        ArrayList<String> listStringsLog = new ArrayList<>();
        String str="";
        int countStrings = 0;  //максимум читаем 100 последних строк из логов
        try (BufferedReader reader = new BufferedReader(new FileReader("chatLog.txt"))) { //название файла-логов
            //заполним лист строк из лога
            String strRead;
            while ((strRead = reader.readLine()) != null) {
                listStringsLog.add(strRead);
                countStrings++;
            }
            //зальем последние countStrings = 100 строк из Листа прочитанных строк лога
            for (int i = listStringsLog.size() - Math.min(MAX_LOG_COUNT_STRINGS, countStrings);
                 i < listStringsLog.size(); i++) {
                str += listStringsLog.get(i) + '\n';
            }
            chatTextArea.setText(str);
        } catch (IOException ignored) {
        }
    }

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
            if (chatClient == null) { //Если соединение с сервером не устанавливалось, то сразу выходим
                //Предварительно закрываем соединение с логом, т.к. оно открыто перманентно
                try {
                    if (logWriter != null) {
                        logWriter.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Platform.exit();
            }
            chatClient.sendMessage("/esc");//Оповещаем сервер о том, что мы выходим: теперь ему можно перестать слушать наш сокет
            try {
                if (!chatClient.socket.isClosed()) { //ждем, если коннект сокета не закрыт
                    monitor.wait(2000); //ждем максимум 2 секунды
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                //Предварительно закрываем соединение с логом, т.к. оно открыто перманентно
                try {
                    if (logWriter != null) {
                        logWriter.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                printChatText("Не удалось подсоединиться к серверу" + "\n");
            }
        }

        public void openConnection() throws IOException {
            socket = new Socket(SERVER_ADDR, SERVER_PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            isConnect = true;
            printChatText("Вы соединились с сервером чата. Нужно авторизоваться." + '\n'
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
                        if (serverMessage.startsWith("/")) {    //блок системных команд
                            String[] parts = serverMessage.split("\\s+");
                            switch (parts[0].toLowerCase()) {
                                case "/kick":
                                    closeConnection(); //Если с сервера прилетает команда /kick, то соединение с ним закрывается
                                    return;
                                case "/authok":  //если ответ сервера: успешная авторизация
                                    authStep = 0;
                                    printChatText("Вы успешно авторизовались в чате как " + parts[1] + '\n');
                                    SendText.setPromptText("Введите текст вашего сообщения ");
                                    break;
                                case "/autherror ":
                                    authStep = 1;   //возвращаемся на шаг ввода логина, если ошибка в логине/пароле нашем
                                    printChatText("Ошибка авторизации под ником " + parts[1] + '\n'
                                            + "Снова введите логин" + '\n');
                                    SendText.setPromptText("Введите логин");
                                    break;
                                case "/clients":    //если сервер передает нам список клиентов чата
                                    clientsTextArea.setText(serverMessage.substring(parts[0].length() + 1));
                                    //выведем присланный нам список клиентов чата
                                    break;
                            }
                        } else {
                            printChatText(serverMessage + "\n");
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();      //Если соединение с сервером обрывается по какой-то причине
                }
            }).start();

        }

        public synchronized void closeConnection() {
            synchronized (monitor) {    //Синхронизируем по монитору, чтобы обеспечить синхронность ивента закрытия нашего окна
                try {
                    if (isConnect) { //Если уже отсоединены, то отсоединять уже не надо
                        in.close();
                        out.close();
                        socket.close();
                        isConnect = false;
                        printChatText("Вы отключились от сервера сетевого чата! " + '\n');
                        ConnectButton.setText("Подсоединиться к серверу");
                        SendText.setVisible(false);
                        SendButton.setVisible(false);
                        monitor.notify();   //оповестили ивент закрытия окна, что сейчас можно выключать это приложение
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public synchronized void sendMessage(String message) {
            try {
                out.writeUTF(message);
                if (authStep == 1) {  //Если мы вводим пока ещё только свой логин в рамках авторизации
                    printChatText("Теперь введите свой пароль" + '\n');
                    SendText.setPromptText("Введите свой пароль");
                    authStep = 2;
                }
            } catch (IOException e) {
                e.printStackTrace();
                printChatText("Ошибка отправки сообщения" + '\n');
            }
        }

        //метод связывающий запись в логи того, что пишется в окно чата
        public synchronized void printChatText(String text) {
            chatTextArea.appendText(text);  //пишем в поле чата всегда
            try {
                if (logWriter == null)
                    logWriter = new BufferedWriter(new FileWriter("chatLog.txt"));
                logWriter.write(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}