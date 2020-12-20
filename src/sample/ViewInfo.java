package sample;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ViewInfo {

    @FXML
    private Label regNo;

    @FXML
    private ColorPicker color1;

    @FXML
    private ColorPicker color2;

    @FXML
    private ColorPicker color3;

    @FXML
    private Label maker;

    @FXML
    private Label model;

    @FXML
    private Label year;

    @FXML
    private Label price;

    @FXML
    private Label quantity;

    public void initialize() {
        NetworkUtil.getInstance().send("VIEWINFO\t");
        new Thread(()-> {
            String receivedData = NetworkUtil.getInstance().receive();
            System.out.println("\n\t\t\t" + receivedData + "\n");
            String[] splited = receivedData.split("\t");

            regNo.setText(splited[0]);
            year.setText(splited[1]);
            color1.setValue(Color.valueOf(splited[2]));
            color2.setValue(Color.valueOf(splited[3]));
            color3.setValue(Color.valueOf(splited[4]));
            maker.setText(splited[5]);
            model.setText(splited[6]);
            price.setText(splited[7]);
            quantity.setText(splited[8]);
        }).start();
    }

    @FXML
    void onReturnButtonPressed() {
        ((Stage)regNo.getScene().getWindow()).close();
    }

}
