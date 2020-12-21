package mortytheplatypus;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class EditCar {

    @FXML
    private TextField editRegNo;

    @FXML
    private TextField editMaker;

    @FXML
    private TextField editModel;

    @FXML
    private ColorPicker editColor1;

    @FXML
    private ColorPicker editColor2;

    @FXML
    private ColorPicker editColor3;

    @FXML
    private TextField editYear;

    @FXML
    private TextField editPrice;

    @FXML
    private TextField editQuantity;

    @FXML
    private Label cautionLabelEdit;

    public void initialize() {
        editRegNo.setDisable(true);
        editYear.setDisable(true);
        editMaker.setDisable(true);
        editMaker.setDisable(true);

        cautionLabelEdit.setDisable(true);

        NetworkUtil.getInstance().send("EDIT\t");
        new Thread(()-> {
            String receivedData = NetworkUtil.getInstance().receive();
            System.out.println("\n\t\t\t" + receivedData + "\n");
            String[] splited = receivedData.split("\t");

            editRegNo.setText(splited[0]);
            editYear.setText(splited[1]);
            editColor1.setValue(Color.valueOf(splited[2]));
            editColor2.setValue(Color.valueOf(splited[3]));
            editColor3.setValue(Color.valueOf(splited[4]));
            editMaker.setText(splited[5]);
            editModel.setText(splited[6]);
            editPrice.setText(splited[7]);
            editQuantity.setText(splited[8]);

        }).start();
    }

    @FXML
    void OnClearPressed() {
        editRegNo.clear();
        editYear.clear();
        editColor1.setValue(Color.WHITE);
        editColor2.setValue(Color.WHITE);
        editColor3.setValue(Color.WHITE);
        editMaker.clear();
        editModel.clear();
        editPrice.clear();
        editQuantity.clear();
    }

    @FXML
    void OnReturnToHomePressed() {
        ((Stage)editRegNo.getScene().getWindow()).close();
    }

    @FXML
    void onConfirmPressed() {
        try {
            int n = Integer.parseInt(editPrice.getText());
            if (n<=0) {
                cautionLabelEdit.setDisable(false);
                editPrice.clear();
            }
        } catch (NumberFormatException e) {
            cautionLabelEdit.setDisable(false);
            editPrice.clear();
        }

        try {
            int n = Integer.parseInt(editYear.getText());
            if (n<=0) {
                cautionLabelEdit.setDisable(false);
                editYear.clear();
            }
        } catch (NumberFormatException e) {
            cautionLabelEdit.setDisable(false);
            editYear.clear();
        }

        try {
            int n = Integer.parseInt(editQuantity.getText());
            if (n<=0) {
                cautionLabelEdit.setDisable(false);
                editQuantity.clear();
            }
        } catch (NumberFormatException e) {
            cautionLabelEdit.setDisable(false);
            editQuantity.clear();
        }

        if (cautionLabelEdit.isDisabled()) {
            String str = "EDITCAR" + "\t" + editRegNo.getText() + "\t" + editYear.getText() + "\t";
            str += editColor1.getValue() + "\t" + editColor2.getValue() + "\t" + editColor3.getValue() + "\t";
            str += editMaker.getText() + "\t" + editModel.getText() + "\t" + editPrice.getText() + "\t" + editQuantity.getText();

            NetworkUtil.getInstance().send(str);

            new Thread(() -> {
                String receivedData = NetworkUtil.getInstance().receive();
                Platform.runLater(() -> {
                    if (receivedData.equals("EDITED")) {
                        new Alert(Alert.AlertType.CONFIRMATION).show();
                    }
                });
            }).start();
        }

        cautionLabelEdit.setDisable(true);
    }

}
