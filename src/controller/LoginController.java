package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.awt.*;
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

    /**
     * Checks the credentials entered against the DB for a match
     * and handles accordingly.
     * @param event
     */
    void loginButtonHandler(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
