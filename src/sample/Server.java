package sample;

import java.net.ServerSocket;
import java.net.Socket;

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
