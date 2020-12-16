package sample;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client implements Runnable {
    private static ArrayList<Car> carArrayList = new ArrayList<>();
    private Socket socket;

    public Client(Socket socket) {
        this.socket = socket;
//        loadCarArrayList(carArrayList);
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
//                System.out.println(clientMessage);

                if (temp[0].equals("LOGIN")) {
                    String loginSuccessful = check(clientMessage);
                    dos.writeUTF(loginSuccessful);
                } else if (temp[0].equals("NEWCAR")) {
                    Car car = new Car(temp[1], Integer.parseInt(temp[2]), temp[3], temp[4], temp[5], temp[6], temp[7], Integer.parseInt(temp[8]), 1);
                    carArrayList.add(car);
                    dos.writeUTF("ADDED");
                } else if (temp[0].equals("VIEWALL")) {
                    loadCarArrayList(carArrayList);

                    dos.writeUTF("VIEWALL\t" + carArrayList.size());

                    for (Car car : carArrayList) {
                        String carInfo = car.getRegistrationNumber() + "\t" + car.getYearMade() + "\t" + car.getColor1() + "\t";
                        carInfo += car.getColor2() + "\t" + car.getColor3() + "\t" + car.getMaker() + "\t" + car.getModel() + "\t";
                        carInfo += car.getPrice() + "\t" + car.getQuantity();

//                        System.out.println(carInfo);

                        dos.writeUTF(carInfo);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCarArrayList(ArrayList<Car> carArrayList) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("D:\\BUET\\Academic\\1-2\\CSE 108\\Car Warehouse\\src\\sample\\car.txt"));
            String carInfoRaw;
            while (true) {
                carInfoRaw = br.readLine();
                if (carInfoRaw == null) break;
                String[] cars = carInfoRaw.split("\t");
                Car newCar = new Car(cars[0], Integer.parseInt(cars[1]), cars[2], cars[3], cars[4], cars[5], cars[6], Integer.parseInt(cars[7]), Integer.parseInt(cars[8]));
//                System.out.println(newCar);
                carArrayList.add(newCar);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String check(String clientUsernameAndPassword) throws IOException {
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
