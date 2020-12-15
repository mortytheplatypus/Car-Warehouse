package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    Server() throws Exception {
        ServerSocket serverSocket = new ServerSocket(55555);

        while (true) {
            Socket clientSocket = serverSocket.accept();

            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

            String clientUsernameAndPassword = dis.readUTF();
            boolean loginSuccessful = check(clientUsernameAndPassword);
            dos.writeBoolean(loginSuccessful);

            clientSocket.close();
        }

    }



    public static void main(String[] args) throws Exception {
        new Server();
    }

    boolean check(String clientUsernameAndPassword) throws Exception {
        String[] usernameAndPass = clientUsernameAndPassword.split(",");
        BufferedReader br = new BufferedReader(new FileReader("D:\\BUET\\Academic\\1-2\\CSE 108 - Arko Sir, Sakib Sir, Sharif Sir\\Car Warehouse\\src\\sample\\UsernamePassword.txt"));
        String pair;
        while (true) {
            pair = br.readLine();
            if (pair == null) break;
            String[] str = pair.split(",");
            if (usernameAndPass[0].equals(str[0])) {
                if (usernameAndPass[1].equals(str[1])) return true;
                else return false;
            }
        }
        br.close();

        return false;
    }
}
