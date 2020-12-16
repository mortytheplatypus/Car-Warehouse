package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) throws Exception {
        new Server();
    }

    Server() throws Exception {
        ServerSocket serverSocket = new ServerSocket(44444);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Client(clientSocket);
        }

    }
}
