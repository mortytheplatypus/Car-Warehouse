package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ManufacturerHome {
    @FXML
    private Button button;

    @FXML
    public void onLogoutButtonPressed(ActionEvent event) throws IOException {
        Parent loginPageParent = FXMLLoader.load(getClass().getResource("Start.fxml"));
        Scene loginPageScene = new Scene(loginPageParent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(loginPageScene);
        stage.show();
    }

    public void addNewCar(ActionEvent event) throws IOException {
        Parent loginPageParent = FXMLLoader.load(getClass().getResource("AddNewCar.fxml"));
        Scene loginPageScene = new Scene(loginPageParent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(loginPageScene);
        stage.show();
    }

    public void viewAllCars(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewAllCarsManufacturer.fxml"));
        Parent loginPageParent = loader.load();

        Scene loginPageScene = new Scene(loginPageParent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(loginPageScene);
        stage.show();

//        ArrayList<Car> carArrayList = new ArrayList<>();
//        Car car = new Car("2332", 2020, "hgask", "hdsag", "adsff", "asdkf", "fuck", 23423, 2);
//        carArrayList.add(car);
//
//        ViewAllCarsManufacturer viewAllCarsManufacturer = loader.getController();
//        viewAllCarsManufacturer.addCar(carArrayList);

    }
}
