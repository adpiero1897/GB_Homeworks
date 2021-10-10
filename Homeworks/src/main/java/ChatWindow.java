import com.sun.deploy.panel.JSmartTextArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;


public class ChatWindow extends JFrame {

    //Текстовые поля чата и поле, куда мы вводим наши сообщения
    JTextArea chatText = new JTextArea();
    JTextArea messageText = new JTextArea("Введите своё сообщение", 4, 5);


    public ChatWindow() throws HeadlessException {

        //Задаём параметры окна
        setTitle("Kilogramm");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(300, 300, 700, 700);

        //Создаем панель с задним фоном
        JPanel panel = new JPanel();
        panel.setBackground(Color.orange);
        panel.setLayout(null);


        //Задаем кнопку отправления сооьшения
        Button sendButton = new Button("Отправить");
        //Устанавливаем размеры элементов: кнопки и полей для текста + задаем им текстовые значения
        chatText.setBounds(100, 100, 400, 300);
        messageText.setBounds(100, 500, 400, 50);
        sendButton.setBounds(520, 500, 100, 50);
        messageText.setToolTipText("Введите текст сообщения");

        //Место, куда вводим текст теперь само будет просить ввести его "СЮДА"
        messageText.addFocusListener(new FocusListener() {
                                         public void focusGained(FocusEvent e) {
                                             messageText.setText("");
                                         }

                                         public void focusLost(FocusEvent e) {
                                             if (messageText.getText().length() == 0) {
                                                 messageText.setText("Введите текст сообщения");
                                             }
                                         }
                                     }
        );

        //При нажатии на кнопку сообщение отсылается, а текст в поле нового сообщения обновляется
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = messageText.getText();
                if (text != "") {
                    chatText.setText(chatText.getText() + "Вы: " + text + '\n');
                    messageText.setText("Введите текст сообщения");    //Очищаем окно нашего сообщения
                }
            }
        });

        //Запрещаем напрямую редактировать поле текста чата
        chatText.setEditable(false);
        //добавляем поля и кнопку отправления
        add(chatText);
        add(messageText);
        add(sendButton);

        //Для того, чтобы текст не уходил за пределы полей, делаем полосы прокруток для окон чата и сообщения
        JScrollPane scrollMessagePane = new JScrollPane(messageText);
        scrollMessagePane.setVisible(true);
        scrollMessagePane.setBounds(100, 500, 400, 50);
        add(scrollMessagePane);
        //... для сообщения
        JScrollPane scrollChatPane = new JScrollPane(chatText);
        scrollChatPane.setVisible(true);
        scrollChatPane.setBounds(100, 100, 400, 300);
        add(scrollChatPane);

        //добавляем панелья с фоном и делаем её видимой
        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }


}


