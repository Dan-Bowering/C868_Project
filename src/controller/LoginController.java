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
import java.sql.Statement;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;
import utility.JDBC;

public class LoginController implements Initializable {

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Label titleLabel;

    @FXML
    private Label timezoneLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Button exitButton;

//    ResourceBundle rb = ResourceBundle.getBundle("utility/LoginForm", Locale.getDefault());

    @Override
    public void initialize(URL location, ResourceBundle rb) {

  //      getLanguage();
    }

  /*  private void getLanguage(){
        try {
            titleLabel.setText(rb.getString("titleLabel"));
            userIdLabel.setText(rb.getString("userIdLabel"));
            passwordLabel.setText(rb.getString("passwordLabel"));
            loginButton.setLabel(rb.getString("loginButton"));
            exitButton.setLabel(rb.getString("exitButton"));
        }
        catch(MissingResourceException e) {
            System.out.println(e);
        }
}
  */

    /**
     * Checks the login credentials and launches the main screen
     * if the credential check passes, otherwise, throws an error.
     * @param event
     * @throws IOException
     */
    public void loginButtonHandler(ActionEvent event) throws IOException {
    //    String loginUsername = usernameTextField.getText();
    //    String loginPassword = passwordTextField.getText();


        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentsScreen.fxml"));
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


}
