package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.UserLogin;
import utility.AppointmentDB;
import utility.UserDB;

import java.beans.beancontext.BeanContext;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
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
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Validates credentials and sets the stage for the main appointment screen
        if (UserDB.validateLogin(loginUsername, loginPassword)) {

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentScreen.fxml"));
            Scene scene = new Scene(root, 950, 560);
            stage.setTitle("Main Screen");
            stage.setScene(scene);
            stage.show();

            UserLogin.logUserActivity(loginUsername, Boolean.TRUE);

            // Checks if any appointments are scheduled within the next 15 minutes and displays accordingly
            ObservableList<Appointment> nextAppointment = AppointmentDB.getAppointmentsIn15Minutes();

            if (AppointmentDB.getAppointmentsIn15Minutes().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setContentText("You do not have any appointments scheduled to start in the next 15 minutes.");
                Optional<ButtonType> result = alert.showAndWait();
            }

            else {
                for (Appointment next : nextAppointment) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setContentText("You have an appointment beginning soon.\n" + "Appointment ID#: " +
                            next.getAppointmentId() + "\n" + "Start Date/Time: " +
                            next.getStart().toLocalDateTime().format(timeFormat));
                    Optional<ButtonType> result = alert.showAndWait();
                }
            }
        }

        // Sends an error message when credential validation fails
        else {
            ResourceBundle rb = ResourceBundle.getBundle("utility/LoginForm", Locale.getDefault());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(loginErrorTitle);
            alert.setContentText(loginErrorMessage);
            alert.showAndWait();
            usernameTextField.clear();
            passwordTextField.clear();

            UserLogin.logUserActivity(loginUsername, Boolean.FALSE);
        }
    }

    /**
     * Exits the login screen when the Exit button is clicked.
     * @param event
     */
    @FXML
    public void exitButtonHandler(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to exit the program?");

        // Lambda expression used to handle the alert response for exiting the program
        alert.showAndWait().ifPresent((result) -> {
            if (result == ButtonType.OK) {
                System.exit(0);
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {

        // Sets all label text/error messages based on system language setting and Time Zone
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
