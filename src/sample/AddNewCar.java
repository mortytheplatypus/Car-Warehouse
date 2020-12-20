package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class AddNewCar {
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

    @FXML
    private TextField newQuantity;

    @FXML
    private Label cautionLabel;

    @FXML
    public void initialize() {
        cautionLabel.setDisable(true);
    }

    @FXML
    public void OnClearPressed() {
        newRegNo.clear();
        newYearOfManufacture.clear();
        newColor1.setValue(Color.WHITE);
        newColor2.setValue(Color.WHITE);
        newColor3.setValue(Color.WHITE);
        newManufacturer.clear();
        newModel.clear();
        newPrice.clear();
        newQuantity.clear();
    }

    @FXML
    public void onConfirmPressed() {
        try {
            int n = Integer.parseInt(newPrice.getText());
            if (n<=0) {
                cautionLabel.setDisable(false);
                newPrice.clear();
            }
        } catch (NumberFormatException e) {
            cautionLabel.setDisable(false);
            newPrice.clear();
        }

        try {
            int n = Integer.parseInt(newYearOfManufacture.getText());
            if (n<=0) {
                cautionLabel.setDisable(false);
                newYearOfManufacture.clear();
            }
        } catch (NumberFormatException e) {
            cautionLabel.setDisable(false);
            newYearOfManufacture.clear();
        }

        try {
            int n = Integer.parseInt(newQuantity.getText());
            if (n<=0) {
                cautionLabel.setDisable(false);
                newQuantity.clear();
            }
        } catch (NumberFormatException e) {
            cautionLabel.setDisable(false);
            newQuantity.clear();
        }

        if (cautionLabel.isDisabled()) {
            String str = "NEWCAR" + "\t" + newRegNo.getText() + "\t" + newYearOfManufacture.getText() + "\t";
            str += newColor1.getValue() + "\t" + newColor2.getValue() + "\t" + newColor3.getValue() + "\t";
            str += newManufacturer.getText() + "\t" + newModel.getText() + "\t" + newPrice.getText() + "\t" + newQuantity.getText();

            NetworkUtil.getInstance().send(str);

            new Thread(() -> {
                String receivedData = NetworkUtil.getInstance().receive();
                Platform.runLater(() -> {
                    if (receivedData.equals("ADDED")) {
                        new Alert(Alert.AlertType.CONFIRMATION).show();
                    }
                });
            }).start();

            newRegNo.clear();
            newYearOfManufacture.clear();
            newColor1.setValue(Color.WHITE);
            newColor2.setValue(Color.WHITE);
            newColor3.setValue(Color.WHITE);
            newManufacturer.clear();
            newModel.clear();
            newPrice.clear();
            newQuantity.clear();
        }

        cautionLabel.setDisable(true);
    }

    @FXML
    public void OnReturnToHomePressed(ActionEvent event) {
        new LoadFXMLPage("ViewAllCarsManufacturer.fxml", event);
    }
}
