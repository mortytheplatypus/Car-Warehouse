package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class ViewAllCarsManufacturer {
    @FXML
    private TableView<Car> carDataTable;

    @FXML
    private TableColumn<Car, String> regNo;

    @FXML
    private TableColumn<Car, Integer> yearMade;

    @FXML
    private TableColumn<Car, String> color1;

    @FXML
    private TableColumn<Car, String> color2;

    @FXML
    private TableColumn<Car, String> color3;

    @FXML
    private TableColumn<Car, String> maker;

    @FXML
    private TableColumn<Car, String> model;

    @FXML
    private TableColumn<Car, Integer> price;

    @FXML
    private TableColumn<Car, Integer> quantity;

    public void initialize() {
        regNo.setCellValueFactory(new PropertyValueFactory<>("registrationNumber"));
        yearMade.setCellValueFactory(new PropertyValueFactory<>("yearMade"));
        color1.setCellValueFactory(new PropertyValueFactory<>("color1"));
        color2.setCellValueFactory(new PropertyValueFactory<>("color2"));
        color3.setCellValueFactory(new PropertyValueFactory<>("color3"));
        maker.setCellValueFactory(new PropertyValueFactory<>("maker"));
        model.setCellValueFactory(new PropertyValueFactory<>("model"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));


        NetworkUtil.getInstance().send("VIEWALL");
        new Thread(()-> {
            String str = NetworkUtil.getInstance().receive();
//            System.out.println(str);
            String[] splited = str.split("\t");
            int n = Integer.parseInt(splited[1]);
            for (int i=0; i<n; i++) {
                String f = NetworkUtil.getInstance().receive();
                System.out.println(f);
            }
        }).start();

    }

    public void addCar(ArrayList<Car> cars) {
        for (var car : cars) {
            carDataTable.getItems().add(car);
        }
    }
}
