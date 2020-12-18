package sample;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client implements Runnable {
    private static final String FILE_NAME = "D:\\BUET\\Academic\\1-2\\CSE 108\\Car Warehouse\\src\\sample\\car.txt";
    private static ArrayList<Car> carArrayList = new ArrayList<>();
    private Socket socket;
    private Car carToBeEdited = null;
    private String carToBeEditedString;

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
//                System.out.println("\t\t\t\t\t\t" + clientMessage);

                if (temp[0].equals("LOGIN")) {
                    String loginSuccessful = check(clientMessage);
                    dos.writeUTF(loginSuccessful);
                } else if (temp[0].equals("NEWCAR")) {
                    Car car = new Car(temp[1], Integer.parseInt(temp[2]), temp[3], temp[4], temp[5], temp[6], temp[7], Integer.parseInt(temp[8]), 1);
                    carArrayList.add(car);
                    saveCarArrayList(carArrayList);
                    dos.writeUTF("ADDED");
                } else if (temp[0].equals("VIEWALL")) {
                    carArrayList.clear();
                    loadCarArrayList(carArrayList);

                    dos.writeUTF("VIEWALL\t" + carArrayList.size());

                    for (Car car : carArrayList) {
                        String carInfo = car.getRegistrationNumber() + "\t" + car.getYearMade() + "\t" + car.getColor1() + "\t";
                        carInfo += car.getColor2() + "\t" + car.getColor3() + "\t" + car.getMaker() + "\t" + car.getModel() + "\t";
                        carInfo += car.getPrice() + "\t" + car.getQuantity();

                        dos.writeUTF(carInfo);
                    }
                } else if (temp[0].equals("DELETE")) {
                    for (Car car : carArrayList) {
                        if (car.getRegistrationNumber().equals(temp[1])) {
                            carArrayList.remove(car);
                            saveCarArrayList(carArrayList);
                            dos.writeUTF("DELETED");
                            break;
                        }
                    }
                } else if (temp[0].equals("EDITREQUEST")) {
                    carToBeEdited = null;
                    for (Car car : carArrayList) {
                        if (car.getRegistrationNumber().equals(temp[1])) {
                            carToBeEdited = car;
                            carToBeEditedString = car.getRegistrationNumber() + "\t" + car.getYearMade() + "\t" + car.getColor1() + "\t";
                            carToBeEditedString += car.getColor2() + "\t" + car.getColor3() + "\t" + car.getMaker() + "\t" + car.getModel() + "\t";
                            carToBeEditedString += car.getPrice() + "\t" + car.getQuantity();
                            break;
                        }
                    }
                } else if (temp[0].equals("EDIT")) {
                    dos.writeUTF(carToBeEditedString);
                } else if (temp[0].equals("EDITCAR")) {
                    for (Car car : carArrayList) {
                        if (car.getRegistrationNumber().equals(temp[1])) {

                            car.setRegistrationNumber(temp[1]);
                            car.setYearMade(Integer.parseInt(temp[2]));
                            car.setColor(temp[3], temp[4], temp[5]);
                            car.setMaker(temp[6]);
                            car.setModel(temp[7]);
                            car.setPrice(Integer.parseInt(temp[8]));
                            car.setQuantity(Integer.parseInt(temp[9]));

                            saveCarArrayList(carArrayList);
                            break;
                        }
                    }
                    dos.writeUTF("EDITED");
                } else if (temp[0].equals("BUY")) {
                    String buyMessage = "NOTBOUGHT";
                    for (Car car : carArrayList) {
                        if (car.getRegistrationNumber().equals(temp[1])) {
                            if (car.getQuantity()>0) {
                                buyMessage = "BOUGHT";
                                car.reduceQuantity();
                            }
                            saveCarArrayList(carArrayList);
                            break;
                        }
                    }
                    dos.writeUTF(buyMessage);
                } else if (temp[0].equals("SEARCH")) {
                    carArrayList.clear();
                    loadCarArrayList(carArrayList);

                    int len=0;
                    int [] indexes = new int[carArrayList.size()];

                    if (temp[1].equals("REG")) {
                        for (Car car : carArrayList) {
                            if (car.getRegistrationNumber().equals(temp[2])) {
                                indexes[len++] = carArrayList.indexOf(car);
                            }
                        }
                    } else if (temp[1].equals("MnM")) {
                        for (Car car : carArrayList) {
                            if (car.getMaker().equals(temp[2]) || car.getModel().equals(temp[2])) {
                                indexes[len++] = carArrayList.indexOf(car);
                            }
                        }
                    }

                    dos.writeUTF("VIEWSELECTED\t" + len);

                    for (int i=0; i<len; i++) {
                        Car car = carArrayList.get(indexes[i]);

                        String carInfo = car.getRegistrationNumber() + "\t" + car.getYearMade() + "\t" + car.getColor1() + "\t";
                        carInfo += car.getColor2() + "\t" + car.getColor3() + "\t" + car.getMaker() + "\t" + car.getModel() + "\t";
                        carInfo += car.getPrice() + "\t" + car.getQuantity();

                        dos.writeUTF(carInfo);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editCarMethod(DataInputStream dis, DataOutputStream dos, String[] temp) {
        Car car = new Car(temp[1], Integer.parseInt(temp[2]), temp[3], temp[4], temp[5], temp[6], temp[7], Integer.parseInt(temp[8]), Integer.parseInt(temp[9]));


    }

    private void saveCarArrayList(ArrayList<Car> carArrayList) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME));
            bw.write("");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Car car : carArrayList) {
            String s = car.getRegistrationNumber() + "\t" + Integer.toString(car.getYearMade()) + "\t";
            s += car.getColor1() + "\t" + car.getColor2() + "\t" +car.getColor3() + "\t";
            s += car.getMaker() + "\t" + car.getModel() + "\t" + Integer.toString(car.getPrice()) + "\t" + Integer.toString(car.getQuantity());
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true));
                bw.write(s);
                bw.write("\n");
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadCarArrayList(ArrayList<Car> carArrayList) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("D:\\BUET\\Academic\\1-2\\CSE 108\\Car Warehouse\\src\\sample\\car.txt"));
            String carInfoRaw;
            while (true) {
                carInfoRaw = br.readLine();
//                System.out.println(carInfoRaw);
                if (carInfoRaw == null) break;
                String[] cars = carInfoRaw.split("\t");
//                System.out.println("\u001B[36m" + "carInfoRaw:\t\t\t" + "\u001B[0m" + carInfoRaw);
//                System.out.println("\u001B[34m" + "carsLength:\t\t\t" + "\u001B[0m" + cars.length);
                Car newCar = new Car(cars[0], Integer.parseInt(cars[1]), cars[2], cars[3], cars[4], cars[5], cars[6], Integer.parseInt(cars[7]), Integer.parseInt(cars[8]));
//                System.out.println(newCar);
                carArrayList.add(newCar);
//                System.out.println("\t\t" + newCar.getRegistrationNumber());
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
