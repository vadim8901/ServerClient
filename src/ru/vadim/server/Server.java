package ru.vadim.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        Socket socket = null;
        try(ServerSocket serverSocket = new ServerSocket(8080)){
            System.out.println("Server running...");
            System.out.println("Waiting for client connection...");
            socket = serverSocket.accept();
            System.out.println("Client connected");
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            Scanner inputScanner = new Scanner(System.in);

            while (true){
                String str = inputStream.readUTF();
                if (str.equals("/end")){
                    break;
                }
                System.out.println(str);
                String sendMessage = inputScanner.nextLine();
                outputStream.writeUTF(sendMessage);
            }
        } catch (IOException e) {
            try {
                throw new MyServerException("No connection", e);
            } catch (MyServerException exception){
                System.out.println("Connection stop");
            }
        }
    }
}
