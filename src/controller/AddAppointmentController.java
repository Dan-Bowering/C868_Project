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
import utility.UserDB;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
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
        String type = typeTextField.getText();
        String startTime = startTimeTextField.getText();
        String endTime = endTimeTextField.getText();
        int customerId = Integer.parseInt(customerIdTextField.getText());
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String contact = contactComboBox.getValue();

        // Convert date and time into LocalDateTime objects, ZoneDateTime objects, then to UTC
        LocalDateTime startLocalDateTime = LocalDateTime.of(startDate, LocalTime.parse(startTime));
        LocalDateTime endLocalDateTime = LocalDateTime.of(endDate, LocalTime.parse(endTime));
        ZonedDateTime zdtStart= ZonedDateTime.of(startLocalDateTime, UserDB.getUserTimeZone());
        ZonedDateTime zdtEnd= ZonedDateTime.of(endLocalDateTime, UserDB.getUserTimeZone());
        ZonedDateTime utcZoneStart = zdtStart.withZoneSameInstant(ZoneOffset.UTC);
        ZonedDateTime utcZoneEnd = zdtEnd.withZoneSameInstant(ZoneOffset.UTC);

        // Sends an error message for failing appointment time validation
        if (!withinBusinessHours(zdtStart, zdtEnd, startDate, endDate)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("The appointment does not fall within our hours of operation or the start time is " +
                    "after the end time.  Please schedule the appointment between 8AM-10PM EST.");
            Optional<ButtonType> result = alert.showAndWait();
        }

        // Sends an error message if updated date/time overlaps an existing appointment date/time
        else if (!AppointmentDB.overlappingAppointments(customerId, startLocalDateTime, endLocalDateTime)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("The appointment you are attempting to schedule conflicts with another " +
                    "appointment on this customer's schedule.  Please choose another date/time.");
            Optional<ButtonType> result = alert.showAndWait();
        }

        else {
            // Add appointment to DB
            AppointmentDB.addAppointment(title, description, location, type, utcZoneStart,
                    utcZoneEnd, customerId, ContactDB.getContactId(contact));

            // Set the stage - Appointment Screen
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentScreen.fxml"));
            Scene scene = new Scene(root, 1000, 520);
            stage.setTitle("Main Screen");
            stage.setScene(scene);
            stage.show();
        }
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
     * Returns to the main appointment screen.
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

    /**
     * Checks that the appointment is within hours of operation (8AM-10PM EST)
     *
     */
    public static boolean withinBusinessHours(ZonedDateTime zdtStart, ZonedDateTime zdtEnd, LocalDate startDate,
                                              LocalDate endDate) {

        ZonedDateTime startZDT = ZonedDateTime.of(LocalDateTime.from(zdtStart), UserDB.getUserTimeZone());
        ZonedDateTime endZDT = ZonedDateTime.of(LocalDateTime.from(zdtEnd), UserDB.getUserTimeZone());
        ZonedDateTime startBusinessHours = ZonedDateTime.of(startDate, LocalTime.of(8, 0),
                ZoneId.of("America/Detroit"));
        ZonedDateTime endBusinessHours = ZonedDateTime.of(endDate, LocalTime.of(22, 0),
                ZoneId.of("America/Detroit"));

        if (startZDT.isBefore(startBusinessHours) || endZDT.isAfter(endBusinessHours) ||
                endZDT.isBefore(startZDT)) {
            return false;
        }
        else {
            return true;
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Set all contact names in the ComboBox
        try {
            contactComboBox.setItems(ContactDB.getAllContactNames());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
