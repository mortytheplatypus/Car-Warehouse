package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

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
    public void OnClearPressed(ActionEvent event) {
        newRegNo.clear();
        newYearOfManufacture.clear();
        newColor1.setValue(Color.WHITE);
        newColor2.setValue(Color.WHITE);
        newColor3.setValue(Color.WHITE);
        newManufacturer.clear();
        newModel.clear();
        newPrice.clear();
    }

    @FXML
    public void onConfirmPressed(ActionEvent event) throws IOException {
        String str = "NEWCAR" + "\t" + newRegNo.getText() + "\t" + newYearOfManufacture.getText() + "\t";
        str += newColor1.getValue() + "\t" + newColor2.getValue() + "\t" + newColor3.getValue() + "\t";
        str += newManufacturer.getText() + "\t" + newModel.getText() + "\t" + newPrice.getText();

        NetworkUtil.getInstance().send(str);

        String finalStr = str;
        new Thread(()-> {
            String receivedData = NetworkUtil.getInstance().receive();
            Platform.runLater(()-> {
                if (receivedData.equals("ADDED")) {
//                    System.out.println(finalStr);
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
    }

    public void OnReturnToHomePressed(ActionEvent event) {
        Parent loginPageParent = null;
        try {
            loginPageParent = FXMLLoader.load(getClass().getResource("ViewAllCarsManufacturer.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene loginPageScene = new Scene(loginPageParent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(loginPageScene);
        stage.show();
    }
}
