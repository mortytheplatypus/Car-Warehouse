package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class LoginController {
    private static final String FILE_NAME = "UsernamePassword.txt";

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
    public void handleKeyReleased(KeyEvent keyEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        boolean disabled = username.isEmpty() || username.trim().isEmpty() || password.isEmpty();
        loginButton.setDisable(disabled);
        cancelButton.setDisable(disabled);
    }

    @FXML
    public void onReturnToMainPage(ActionEvent event) throws IOException {
        Parent loginPageParent = FXMLLoader.load(getClass().getResource("Start.fxml"));
        Scene loginPageScene = new Scene(loginPageParent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(loginPageScene);
        stage.show();
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
                    Parent loginPageParent = null;
                    try {
                        loginPageParent = FXMLLoader.load(getClass().getResource("ManufacturerHome.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene loginPageScene = new Scene(loginPageParent);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(loginPageScene);
                    stage.show();
                }
            });

        }).start();

    }

}
