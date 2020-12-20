package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private Button cancelButton;
    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label warning;

    @FXML
    public void initialize() {
        loginButton.setDisable(true);
        cancelButton.setDisable(true);
    }

    @FXML
    public void onCancelButtonPressed() {
        usernameField.clear();
        passwordField.clear();
        loginButton.setDisable(true);
        cancelButton.setDisable(true);
    }

    @FXML
    public void handleKeyReleased() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        boolean disabled = username.isEmpty() || username.trim().isEmpty() || password.isEmpty();
        loginButton.setDisable(disabled);
        cancelButton.setDisable(disabled);
    }

    @FXML
    public void onReturnToMainPage(ActionEvent event) {
        new LoadFXMLPage("Start.fxml", event);
    }

    @FXML
    public void onLoginButtonPressed(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String usernameAndPassword = "LOGIN" + "\t" + username + "\t" + password;

        NetworkUtil.getInstance().send(usernameAndPassword);

        new Thread(() -> {
            String receivedData = NetworkUtil.getInstance().receive();
            Platform.runLater(()-> {
                if (receivedData.equals("FALSE")) {
                    warning.setText("  Wrong username or password. Try again.");
                } else {
                    new LoadFXMLPage("ViewAllCarsManufacturer.fxml", event);
                }
            });
        }).start();
    }
}
