package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class StartController {

    @FXML
    public void onManufacturerButtonPressed(ActionEvent event) {
        new LoadFXMLPage("Login.fxml", event);
    }

    @FXML
    public void onViewerButtonPressed(ActionEvent event) {
        new LoadFXMLPage("ViewAllCarsViewer.fxml", event);
    }

    @FXML
    public void onExitButtonPressed() {
        Platform.exit();
    }
}
