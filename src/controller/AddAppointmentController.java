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
import utility.AppointmentDB;
import utility.ContactDB;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

    @FXML TextField appointmentIdTextField;
    @FXML TextField titleTextField;
    @FXML TextField descriptionTextField;
    @FXML TextField locationTextField;
    @FXML TextField typeTextField;
    @FXML TextField startTimeTextField;
    @FXML TextField endTimeTextField;
    @FXML TextField customerIdTextField;
    @FXML ComboBox<String> contactComboBox;
    @FXML DatePicker startDatePicker;
    @FXML DatePicker endDatePicker;

    /**
     * Saves the new appointment information entered into the
     * add appointment form.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    public void saveAddAppointmentButtonHandler(ActionEvent event) throws IOException, SQLException {

        String title = titleTextField.getText();
        String description = descriptionTextField.getText();
        String location = locationTextField.getText();
        String type = locationTextField.getText();
        String startTime = startTimeTextField.getText();
        String endTime = endTimeTextField.getText();
        int customerId = Integer.parseInt(customerIdTextField.getText());
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String contact = contactComboBox.getValue();


        LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.parse(startTime));
        LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.parse(endTime));

        AppointmentDB.addAppointment(title, description, location, type, startDateTime,
                                    endDateTime, customerId, ContactDB.getContactId(contact));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentScreen.fxml"));
        Scene scene = new Scene(root, 1000, 520);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Navigates back to the main Appointments table without saving chaanges
     * @param event
     */@FXML
    public void cancelButtonHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to go back?  All changes will be lost.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            toAppointmentScreen(event);
        }
    }

    /**
     * Returns to the main screen.
     * @param event
     * @throws IOException
     */
    @FXML
    public void toAppointmentScreen(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            contactComboBox.setItems(ContactDB.getAllContactNames());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
