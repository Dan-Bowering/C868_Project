package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utility.UserDB;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private Label usernameLabel;
    @FXML private Label passwordLabel;
    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;
    @FXML private Label titleLabel;
    @FXML private Label zoneIdLabel;
    @FXML private Button loginButton;
    @FXML private Button exitButton;

    private String loginErrorTitle;
    private String loginErrorMessage;

    /**
     * Checks the login credentials and launches the appointment screen
     * if the credential check passes, otherwise, throws an error.
     * @param event
     * @throws IOException
     */
    @FXML
    public void loginButtonHandler(ActionEvent event) throws IOException, SQLException {
       
        String loginUsername = usernameTextField.getText();
        String loginPassword = passwordTextField.getText();

        if (UserDB.validateLogin(loginUsername, loginPassword)) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentScreen.fxml"));
            Scene scene = new Scene(root, 950, 520);
            stage.setTitle("Main Screen");
            stage.setScene(scene);
            stage.show();
        }

        else {
            ResourceBundle rb = ResourceBundle.getBundle("utility/LoginForm", Locale.getDefault());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(loginErrorTitle);
            alert.setContentText(loginErrorMessage);
            alert.showAndWait();
            usernameTextField.clear();
            passwordTextField.clear();
        }
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
    public void initialize(URL location, ResourceBundle resourceBundle) {
        ResourceBundle rb = ResourceBundle.getBundle("utility/LoginForm", Locale.getDefault());
        titleLabel.setText(rb.getString("titleLabel"));
        usernameLabel.setText(rb.getString("usernameLabel"));
        passwordLabel.setText(rb.getString("passwordLabel"));
        zoneIdLabel.setText(rb.getString("zoneIdLabel"));
        loginButton.setText(rb.getString("loginButton"));
        exitButton.setText(rb.getString("exitButton"));
        loginErrorTitle = rb.getString("loginErrorTitle");
        loginErrorMessage = rb.getString("loginErrorMessage");
        zoneIdLabel.setText(Calendar.getInstance().getTimeZone().getID());
    }

}
