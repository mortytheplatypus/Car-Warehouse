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

public class SearchCar {

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchArea;

    @FXML
    private RadioButton byRegistration;

    @FXML
    private RadioButton byMakerAndModel;

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
    }

    @FXML
    public void onViewAll(ActionEvent event) {
        new LoadFXMLPage("ViewAllCarsViewer.fxml", event);
    }

    @FXML
    public void onSearchClicked() {

        String searchCar = searchArea.getText().toLowerCase();
        String sendStr = "SEARCH\t";
        if (byRegistration.isSelected()) {
            sendStr += "REG\t";
        } else if (byMakerAndModel.isSelected()){
            sendStr += "MnM\t";
        }

        sendStr += searchCar;
        NetworkUtil.getInstance().send(sendStr);

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

        searchButton.setDisable(true);
    }

    public void refreshThisPage(ActionEvent event) {
        new LoadFXMLPage("SearchCar.fxml", event);

    }

    @FXML
    public void onViewInfoSearch() {
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

    @FXML
    public void onBuyContextMenuSearch() {
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
}
