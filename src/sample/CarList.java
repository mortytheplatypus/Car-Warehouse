package sample;

import java.util.ArrayList;
import java.util.Scanner;

public class CarList {
    Scanner scanner = new Scanner(System.in);

    private ArrayList<Car> carArrayList = new ArrayList<>();

    public void loadCar(Car car) {
        carArrayList.add(car);
    }

    public ArrayList getCarArrayList() {
        return carArrayList;
    }
}