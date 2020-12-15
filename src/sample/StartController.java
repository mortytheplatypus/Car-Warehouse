package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StartController {

    @FXML
    private Button nextScene;

    @FXML
    public void onManufacturerButtonPressed(ActionEvent event) throws Exception {
        Parent loginPageParent = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene loginPageScene = new Scene(loginPageParent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(loginPageScene);
        stage.show();
    }

    public void onViewerButtonPressed(ActionEvent event) {
    }


    public void onExitButtonPressed(ActionEvent event) {
        Platform.exit();
    }
}
