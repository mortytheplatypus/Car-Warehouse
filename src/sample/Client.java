package sample;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client implements Runnable {
    private static ArrayList<Car> carArrayList = new ArrayList<>();
    private Socket socket;

    public Client(Socket socket) {
        this.socket = socket;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            while (true) {
                String clientMessage = dis.readUTF();
                String[] temp = clientMessage.split("\t");
                System.out.println(clientMessage);


                if (temp[0].equals("LOGIN")) {
                    String loginSuccessful = check(clientMessage);
                    dos.writeUTF(loginSuccessful);
                } else if (temp[0].equals("NEWCAR")) {
                    Car car = new Car(temp[1], Integer.parseInt(temp[2]), temp[3], temp[4], temp[5], temp[6], temp[7], Integer.parseInt(temp[8]), 1);
                    carArrayList.add(car);
                    dos.writeUTF("ADDED");
                } else if (temp[0].equals("VIEWALL")) {
                    dos.writeUTF("VIEWALL\t" + carArrayList.size());
                    for (Car car : carArrayList) {
                        dos.writeUTF(car.getRegistrationNumber());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String check(String clientUsernameAndPassword) throws IOException {
        String[] usernameAndPass = clientUsernameAndPassword.split("\t");
        BufferedReader br = new BufferedReader(new FileReader("D:\\BUET\\Academic\\1-2\\CSE 108\\Car Warehouse\\src\\sample\\UsernamePassword.txt"));
        String pair;
        while (true) {
            pair = br.readLine();
            if (pair == null) break;
            String[] str = pair.split("\t");
            if (usernameAndPass[1].equals(str[0])) {
                if (usernameAndPass[2].equals(str[1])) return "TRUE";
                else return "FALSE";
            }
        }
        br.close();

        return "FALSE";
    }
}
