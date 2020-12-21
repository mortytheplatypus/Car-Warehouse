package mortytheplatypus;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewAllCarsViewer {
    @FXML
    private TableView<Car> carDataTable;

    @FXML
    private TableColumn<Car, String> regNo;

    @FXML
    private TableColumn<Car, String> yearMade;

    @FXML
    private TableColumn<Car, String> color1;

    @FXML
    private TableColumn<Car, String> color2;

    @FXML
    private TableColumn<Car, String> color3;

    @FXML
    private TableColumn<Car, String>maker;

    @FXML
    private TableColumn<Car, String> model;

    @FXML
    private TableColumn<Car, String> price;

    @FXML
    private TableColumn<Car, String> quantity;

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

        refreshViewer();
    }

    private void refreshViewer() {
        NetworkUtil.getInstance().send("VIEWALL\t");

        new Thread(()-> {
            String str = NetworkUtil.getInstance().receive();
            String[] splited = str.split("\t");
            int n = Integer.parseInt(splited[1]);
            Platform.runLater(()-> carDataTable.getItems().clear());
            for (int i=0; i<n; i++) {
                String carInfo = NetworkUtil.getInstance().receive();
                String[] s = carInfo.split("\t");
                Car car = new Car(s[0], Integer.parseInt(s[1]), s[2], s[3], s[4], s[5], s[6], Integer.parseInt(s[7]), Integer.parseInt(s[8]));
                Platform.runLater(()-> carDataTable.getItems().add(car));
            }
        }).start();
    }

    @FXML
    void refreshThisPage()  {
        refreshViewer();
    }

    @FXML
    public void returnToMainPage(ActionEvent event) {
        new LoadFXMLPage("Start.fxml", event);
    }

    @FXML
    public void onBuyContextMenu() {
        Car car = carDataTable.getSelectionModel().getSelectedItem();

        NetworkUtil.getInstance().send("BUY\t" + car.getRegistrationNumber());

        new Thread(()-> {
            String buyingMessage = NetworkUtil.getInstance().receive();
            Platform.runLater(()-> {
                if (buyingMessage.equals("BOUGHT")) {
                    new Alert(Alert.AlertType.CONFIRMATION).show();
                } else {
                    new Alert((Alert.AlertType.ERROR)).show();
                }
            });
        }).start();
    }

    @FXML
    public void onSearchCarClicked(ActionEvent event) {
        new LoadFXMLPage("SearchCar.fxml", event);
    }

    @FXML
    public void onViewInfoViewer() {
        Car car = carDataTable.getSelectionModel().getSelectedItem();

        NetworkUtil.getInstance().send("VIEWINFOREQUEST\t" + car.getRegistrationNumber());

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("ViewInfo.fxml"));
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage info = new Stage();
        info.setScene(new Scene(parent));
        info.showAndWait();
    }
}
