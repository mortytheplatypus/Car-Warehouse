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

import java.io.IOException;

public class StartController {

    @FXML
    public void onManufacturerButtonPressed(ActionEvent event) throws IOException {
        new LoadFXMLPage("Login.fxml", event);
    }

    @FXML
    public void onViewerButtonPressed(ActionEvent event) throws IOException {
        new LoadFXMLPage("ViewAllCarsViewer.fxml", event);
    }

    @FXML
    public void onExitButtonPressed(ActionEvent event) {
        Platform.exit();
    }
}
