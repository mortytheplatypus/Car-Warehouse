package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoadFXMLPage {
    LoadFXMLPage(String fileName, ActionEvent event) {
        Parent loginPageParent = null;
        try {
            loginPageParent = FXMLLoader.load(getClass().getResource(fileName));
        } catch (IOException e) {
//            e.printStackTrace();
        }
        Scene loginPageScene = new Scene(loginPageParent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(loginPageScene);
        stage.show();
    }
}
