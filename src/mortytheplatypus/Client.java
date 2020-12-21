package mortytheplatypus;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client implements Runnable {
    private static final String CAR_DATA_FILE_NAME = "D:\\BUET\\Academic\\1-2\\CSE 108\\Car Warehouse\\car.txt";
    private static final String USERNAME_PASSWORD_FILE = "D:\\BUET\\Academic\\1-2\\CSE 108\\Car Warehouse\\UsernamePassword.txt";
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

                if (temp[0].equals("LOGIN")) {
                    String loginSuccessful = check(clientMessage);
                    dos.writeUTF(loginSuccessful);
                } else if (temp[0].equals("NEWCAR")) {
                    Car car = new Car(temp[1], Integer.parseInt(temp[2]), temp[3], temp[4], temp[5], temp[6], temp[7], Integer.parseInt(temp[8]), Integer.parseInt(temp[9]));

                    String isAdded = "ADDED";

                    for (Car c : carArrayList) {
                        if (c.getRegistrationNumber().toLowerCase().equals(temp[1].toLowerCase())) {
                            isAdded = "NOTADDED";
                            break;
                        }
                    }
                    if (isAdded.equals("ADDED")) {
                        carArrayList.add(car);
                        saveCarArrayList(carArrayList);
                    }
                    dos.writeUTF(isAdded);
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
                } else if (temp[0].equals("EDITREQUEST") || temp[0].equals("VIEWINFOREQUEST")) {
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
                } else if (temp[0].equals("EDIT") || temp[0].equals("VIEWINFO")) {
                    dos.writeUTF(carToBeEditedString);
                } else if (temp[0].equals("EDITCAR")) {
                    for (Car car : carArrayList) {
                        if (car.getRegistrationNumber().toLowerCase().equals(temp[1].toLowerCase())) {

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
                            if (car.getRegistrationNumber().toLowerCase().equals(temp[2].toLowerCase())) {
                                indexes[len++] = carArrayList.indexOf(car);
                            }
                        }
                    } else if (temp[1].equals("MnM")) {
                        for (Car car : carArrayList) {
                            if (car.getMaker().toLowerCase().equals(temp[2].toLowerCase()) || car.getModel().toLowerCase().equals(temp[2].toLowerCase())) {
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

    private void saveCarArrayList(ArrayList<Car> carArrayList) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(CAR_DATA_FILE_NAME));
            bw.write("");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Car car : carArrayList) {
            String s = car.getRegistrationNumber() + "\t" + car.getYearMade() + "\t";
            s += car.getColor1() + "\t" + car.getColor2() + "\t" +car.getColor3() + "\t";
            s += car.getMaker() + "\t" + car.getModel() + "\t" + car.getPrice() + "\t" + car.getQuantity();
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(CAR_DATA_FILE_NAME, true));
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
            BufferedReader br = new BufferedReader(new FileReader(CAR_DATA_FILE_NAME));
            String carInfoRaw;
            while (true) {
                carInfoRaw = br.readLine();
                if (carInfoRaw == null) break;
                String[] cars = carInfoRaw.split("\t");
                if (Integer.parseInt(cars[8])<=0) continue;
                Car newCar = new Car(cars[0], Integer.parseInt(cars[1]), cars[2], cars[3], cars[4], cars[5], cars[6], Integer.parseInt(cars[7]), Integer.parseInt(cars[8]));
                carArrayList.add(newCar);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String check(String clientUsernameAndPassword) throws IOException {
        String[] usernameAndPass = clientUsernameAndPassword.split("\t");
        BufferedReader br = new BufferedReader(new FileReader(USERNAME_PASSWORD_FILE));
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
