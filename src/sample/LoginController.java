package sample;

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

    public void handleKeyReleased(KeyEvent keyEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        boolean disabled = username.isEmpty() || username.trim().isEmpty() || password.isEmpty();
        loginButton.setDisable(disabled);
        cancelButton.setDisable(disabled);
    }

    public void onReturnToMainPage(ActionEvent event) throws IOException {
        Parent loginPageParent = FXMLLoader.load(getClass().getResource("Start.fxml"));
        Scene loginPageScene = new Scene(loginPageParent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(loginPageScene);
        stage.show();
    }

    @FXML
    public void onLoginButtonPressed(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String usernameAndPassword = "LOGIN" + "\t" + username + "\t" + password;

        Socket socket = new Socket("127.0.0.1", 55555);

        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        dataOutputStream.writeUTF(usernameAndPassword);

        if (!dataInputStream.readBoolean()) {
            warning.setText("  Wrong username or password. Try again.");
        } else {
            Parent loginPageParent = FXMLLoader.load(getClass().getResource("ManufacturerHome.fxml"));
            Scene loginPageScene = new Scene(loginPageParent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(loginPageScene);
            stage.show();
        }
    }

}
