package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

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
    private Button cancelOnAddNewCar;

    @FXML
    public Button confirmOnAddNewCar;

    @FXML
    public void OnCancelPressed(ActionEvent event) throws Exception {
        Parent loginPageParent = FXMLLoader.load(getClass().getResource("ManufacturerHome.fxml"));
        Scene loginPageScene = new Scene(loginPageParent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(loginPageScene);
        stage.show();
    }


    @FXML
    public void onConfirmPressed(ActionEvent event) throws IOException {
        String str = "NEWCAR" + "\t" + newRegNo.getText() + "\t" + newYearOfManufacture.getText() + "\t";
        str += newColor1.getValue() + "\t" + newColor2.getValue() + "\t" + newColor3.getValue() + "\t";
        str += newManufacturer + "\t" + newModel + "\t" + newPrice;

        Socket socket = new Socket("localhost", 55555);
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        dataOutputStream.writeUTF(str);
    }
}


