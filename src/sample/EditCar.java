package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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

    @FXML
    private TextField newQuantity;

    public void initialize() {
        NetworkUtil.getInstance().send("EDIT\t");
        new Thread(()-> {
            String receivedData = NetworkUtil.getInstance().receive();
            System.out.println("\n\t\t\t" + receivedData + "\n");
            String[] splited = receivedData.split("\t");

            newRegNo.setText(splited[0]);
            newYearOfManufacture.setText(splited[1]);
            newColor1.setValue(Color.valueOf(splited[2]));
            newColor2.setValue(Color.valueOf(splited[3]));
            newColor3.setValue(Color.valueOf(splited[4]));
            newManufacturer.setText(splited[5]);
            newModel.setText(splited[6]);
            newPrice.setText(splited[7]);
            newQuantity.setText(splited[8]);

        }).start();
    }

    @FXML
    void OnClearPressed(ActionEvent event) {
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
    void OnReturnToHomePressed(ActionEvent event) {
//        new LoadFXMLPage("ViewAllCarsManufacturer.fxml", event);
        ((Stage)newQuantity.getScene().getWindow()).close();
    }

    @FXML
    void onConfirmPressed(ActionEvent event) {
        String str = "EDITCAR" + "\t" + newRegNo.getText() + "\t" + newYearOfManufacture.getText() + "\t";
        str += newColor1.getValue() + "\t" + newColor2.getValue() + "\t" + newColor3.getValue() + "\t";
        str += newManufacturer.getText() + "\t" + newModel.getText() + "\t" + newPrice.getText() + "\t" + newQuantity.getText();

        NetworkUtil.getInstance().send(str);

        String finalStr = str;
        new Thread(()-> {
            String receivedData = NetworkUtil.getInstance().receive();
            Platform.runLater(()-> {
                if (receivedData.equals("EDITED")) {
//                    System.out.println(finalStr);
                    new Alert(Alert.AlertType.CONFIRMATION).show();
                }
            });
        }).start();
    }

}
