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
        ServerSocket serverSocket = new ServerSocket(55555);

        while (true) {
            Socket clientSocket = serverSocket.accept();

            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

            String clientMessage = dis.readUTF();
            String[] temp = clientMessage.split("\t");


            if (temp[0].equals("LOGIN")) {
                boolean loginSuccessful = check(clientMessage);
                dos.writeBoolean(loginSuccessful);
            } else if (temp[0].equals("NEWCAR")) {
//                addNewCar(clientMessage, carArrayList);
            }

            clientSocket.close();
        }

    }

    boolean check(String clientUsernameAndPassword) throws Exception {
        String[] usernameAndPass = clientUsernameAndPassword.split("\t");
//        System.out.println("Client:");
//        System.out.println(usernameAndPass[1]);
//        System.out.println(usernameAndPass[2]);
        BufferedReader br = new BufferedReader(new FileReader("D:\\BUET\\Academic\\1-2\\CSE 108\\Car Warehouse\\src\\sample\\UsernamePassword.txt"));
        String pair;
        while (true) {
            pair = br.readLine();
            if (pair == null) break;
            String[] str = pair.split("\t");
//            System.out.println("Server:");
//            System.out.println(str[0]);
//            System.out.println(str[1]);
            if (usernameAndPass[1].equals(str[0])) {
                if (usernameAndPass[2].equals(str[1])) return true;
                else return false;
            }
        }
        br.close();

        return false;
    }

    private void addNewCar(String clientMessage, ArrayList<Car> carArrayList) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader("D:\\BUET\\Academic\\1-2\\CSE 108\\Car Warehouse\\src\\sample\\car.txt"));

        String[] info = clientMessage.split("\t");
    }
}
