package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ViewAllCarsManufacturer {
    @FXML
    private TableView<Car> carDataTable = new TableView<>();

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


        NetworkUtil.getInstance().send("VIEWALL\t");

        new Thread(()-> {
            String str = NetworkUtil.getInstance().receive();
//            System.out.println("\t\t\t\t\t\t" + str);
            String[] splited = str.split("\t");
            int n = Integer.parseInt(splited[1]);
            for (int i=0; i<n; i++) {
                String carInfo = NetworkUtil.getInstance().receive();
                String[] s = carInfo.split("\t");
                System.out.println("\u001B[36m" + carInfo);
                Car car = new Car(s[0], Integer.parseInt(s[1]), s[2], s[3], s[4], s[5], s[6], Integer.parseInt(s[7]), Integer.parseInt(s[8]));
                carDataTable.getItems().add(car);
            }
        }).start();
    }

    public void onLogoutManufPressed(ActionEvent event) {
        Parent logoutParent = null;
        try {
            logoutParent = FXMLLoader.load(getClass().getResource("Start.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene loginPageScene = new Scene(logoutParent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(loginPageScene);
        stage.show();
    }

//    public void onReturnToHomeManufPressed(ActionEvent event) {
//        Parent manufacturerHomeParent = null;
//        try {
//            manufacturerHomeParent = FXMLLoader.load(getClass().getResource("ManufacturerHome.fxml"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Scene loginPageScene = new Scene(manufacturerHomeParent);
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.setScene(loginPageScene);
//        stage.show();
//    }

    public void onAddNewCarButtonPressed(ActionEvent event) throws IOException {
        Parent loginPageParent = FXMLLoader.load(getClass().getResource("AddNewCar.fxml"));
        Scene loginPageScene = new Scene(loginPageParent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(loginPageScene);
        stage.show();
    }
}
