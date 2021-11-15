package ru.vadim.client;

import javax.management.ObjectName;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends JFrame {
    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 8080;
    DataInputStream inputStream;
    DataOutputStream outputStream;
    TextArea dialog;
    Socket socket;
    JTextField message;

    public static void main(String[] args) {
        new Client();
    }

    public Client(){
        connection();
        createGUI();
        readMessageFromServer();
    }

    private void connection(){
        try {
            socket = new Socket(SERVER_ADDR, SERVER_PORT);
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new MyClientException("No connection to server", e);
        }
    }

    private void readMessageFromServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        String serverMessage = inputStream.readUTF();
                        if (serverMessage.equalsIgnoreCase("/end")){
                            break;
                        }
                        dialog.appendText(serverMessage + "\n");
                    } catch (IOException e) {
                        throw new MyClientException("message cannot be read", e);
                    }

                }
            }
        }).start();
    }

    private void sendMessage(String ms){
        if (!message.getText().trim().isEmpty()) {
            try {
                outputStream.writeUTF(ms);
                message.setText("");
                message.grabFocus();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Ошибка отправки сообщения");
                throw new MyClientException("Error sending message", e);
            }

        }

    }

    private void createGUI(){
        setTitle("OnlineChat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(700,300,300,600);
        setLayout(null);
        dialog = new TextArea();
        dialog.setBounds(0,0,300, 400);
        add(dialog);
        dialog.setEditable(false);
        message = new JTextField();
        message.setBounds(0,420,300,50);
        add(message);
        JButton buttonEnter = new JButton("Enter");
        buttonEnter.setBounds(100, 480, 100,50);
        add(buttonEnter);
        buttonEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ms = message.getText();
                dialog.appendText(ms + "\n");
                sendMessage(ms);
            }
        });
        setVisible(true);
    }
}
