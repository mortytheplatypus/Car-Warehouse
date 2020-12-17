package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;

public class EditCar {

    @FXML
    private TextField newRegNo;

    @FXML
    private TextField newManufacturer;

    @FXML
    private TextField newModel;

    @FXML
    private ColorPicker newColor1;

    @FXML
    private ColorPicker newColor2;

    @FXML
    private ColorPicker newColor3;

    @FXML
    private TextField newYearOfManufacture;

    @FXML
    private TextField newPrice;

    public void initialize() {
        String str = NetworkUtil.getInstance().receive();
        String[] splitedStr = str.split("\t");
        newRegNo.setText("12345");
    }

    @FXML
    void OnClearPressed(ActionEvent event) {

    }

    @FXML
    void OnReturnToHomePressed(ActionEvent event) {

    }

    @FXML
    void onConfirmPressed(ActionEvent event) {

    }

}
