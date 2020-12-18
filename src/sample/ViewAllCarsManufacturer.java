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
import java.util.Optional;

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
            String[] splited = str.split("\t");
            int n = Integer.parseInt(splited[1]);
            for (int i=0; i<n; i++) {
                String carInfo = NetworkUtil.getInstance().receive();
                String[] s = carInfo.split("\t");
                Car car = new Car(s[0], Integer.parseInt(s[1]), s[2], s[3], s[4], s[5], s[6], Integer.parseInt(s[7]), Integer.parseInt(s[8]));
                carDataTable.getItems().add(car);
            }
        }).start();
    }

    @FXML
    public void onLogoutManufPressed(ActionEvent event) {
        new LoadFXMLPage("Start.fxml", event);
    }

    @FXML
    public void onAddNewCarButtonPressed(ActionEvent event) throws IOException {
        new LoadFXMLPage("AddNewCar.fxml", event);
    }

    @FXML
    public void onDeleteContextMenu(ActionEvent event) {
        Car car = carDataTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete car");
        alert.setHeaderText("Delete car: " + car.getRegistrationNumber());
        alert.setContentText("Are you sure? Press OK to confirm, or CANCEL to back out.");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            NetworkUtil.getInstance().send("DELETE\t" + car.getRegistrationNumber());

            new Thread(()-> {
                String receivedData = NetworkUtil.getInstance().receive();
                if (receivedData.equals("DELETED")) {
                    new LoadFXMLPage("ViewAllCarsManufacturer.fxml", event);

                }
            }).start();
        }
    }

    public void onEditContextMenu() throws IOException {
        Car car = carDataTable.getSelectionModel().getSelectedItem();

        NetworkUtil.getInstance().send("EDITREQUEST\t" + car.getRegistrationNumber());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("EditCar.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        new Thread(()-> {
//            String receivedData = NetworkUtil.getInstance().receive();
//            System.out.println(receivedData);
//            if (receivedData.equals("CARINSTANTIATED")) {
////                new LoadFXMLPage("EditCar.fxml", event);
//                FXMLLoader fxmlLoader = new FXMLLoader();
//                fxmlLoader.setLocation(getClass().getResource("EditCar.fxml"));
//                try {
//                    fxmlLoader.load();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

    @FXML
    private void refreshThisPage(ActionEvent event) throws IOException {
        new LoadFXMLPage("ViewAllCarsManufacturer.fxml", event);
    }
}
