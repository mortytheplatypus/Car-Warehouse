package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class ViewAllCarsManufacturer {
    @FXML
    private AnchorPane mainAnchorPane;

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
//        System.out.println("\t\t\t\t\tInitialized baby");
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

    @FXML
    public void onAddNewCarButtonPressed(ActionEvent event) throws IOException {
        Parent loginPageParent = FXMLLoader.load(getClass().getResource("AddNewCar.fxml"));
        Scene loginPageScene = new Scene(loginPageParent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(loginPageScene);
        stage.show();
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

//            new Thread(()-> {
//                String receivedData = NetworkUtil.getInstance().receive();
//                Platform.runLater(()-> {
//                    if (receivedData.equals("DELETED")) {
//                       new Alert(Alert.AlertType.).setContentText("Car with registration no. " + car.getRegistrationNumber() + " is removed successfully.");
//                    }
//                    //////////////////////////////////////////////////////////////////////////////////////////
//                    //////////////////////////////////////////////////////////////////////////////////////////
//                });
//            }).start();
        }
    }

    @FXML
    private void refreshThisPage(ActionEvent event) throws IOException {
        Parent loginPageParent = FXMLLoader.load(getClass().getResource("ViewAllCarsManufacturer.fxml"));
        Scene loginPageScene = new Scene(loginPageParent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(loginPageScene);
        stage.show();
    }

    public void onEditContextMenu(ActionEvent event) {
        Car car = carDataTable.getSelectionModel().getSelectedItem();

        NetworkUtil.getInstance().send("EDIT\t" + car.toString());

        new Thread(()-> {
            String receivedData = NetworkUtil.getInstance().receive();

        }).start();

//        Dialog<ButtonType> dialog = new Dialog<>();
//        dialog.initOwner(mainAnchorPane.getScene().getWindow());
//        dialog.setTitle("Edit car information");
//        FXMLLoader fxmlLoader = new FXMLLoader();
//
//        fxmlLoader.setLocation(getClass().getResource("D:\\BUET\\Academic\\1-2\\CSE 108\\Car Warehouse\\src\\sample\\EditCar.fxml"));
//
//        try {
//            dialog.getDialogPane().setContent(fxmlLoader.load());
//        } catch (IOException e) {
//            e.printStackTrace();
//            return;
//        }
//
//        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
//        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
//
//        Optional<ButtonType> result = dialog.showAndWait();
//        if (result.isPresent() && result.get()==ButtonType.OK) {
//
//        }
    }
}
