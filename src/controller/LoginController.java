package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Label userIdLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField userIdTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Label timezoneLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Button exitButton;

    /**
     * Checks the login credentials and launches the main screen
     * if the credential check passes, otherwise, throws an error.
     * @param event
     * @throws IOException
     */
    public void loginButtonHandler(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Scene scene = new Scene(root, 950, 520);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Exits the login screen when the Exit button is clicked.
     * @param event
     */
    @FXML
    void exitButtonHandler(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to exit the program?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
