package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import utility.AppointmentDB;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable {

    @FXML TableView<Appointment> appointmentTableView;
    @FXML TableColumn<Appointment, Integer> appointmentIdColumn;
    @FXML TableColumn<Appointment, String> titleColumn;
    @FXML TableColumn<Appointment, String> descriptionColumn;
    @FXML TableColumn<Appointment, String> locationColumn;
    @FXML TableColumn<Appointment, String> typeColumn;
    @FXML TableColumn<Appointment, ZonedDateTime> startDateTimeColumn;
    @FXML TableColumn<Appointment, ZonedDateTime> endDateTimeColumn;
    @FXML TableColumn<Appointment, Integer> customerIdColumn;
    @FXML TableColumn<Appointment, Integer> contactIdColumn;

    public static Appointment appointmentToUpdate = null;
    public static Appointment appointmentToDelete = null;

    public static Appointment getAppointmentToUpdate() {
        return appointmentToUpdate;
    }

    /**
     * Switches to the customer list screen when "view customer list"
     * is clicked.
     * @param event
     */
    @FXML
    void viewCustomerListHandler(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
        Scene scene = new Scene(root, 1000, 520);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void addAppointmentHandler(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointmentForm.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads the update appointment form with the selected appointment info.
     * @param event
     * @throws IOException
     */
    @FXML
    public void updateAppointmentHandler(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/UpdateAppointment.fxml"));
        loader.load();

        appointmentToUpdate = appointmentTableView.getSelectionModel().getSelectedItem();

        if (appointmentToUpdate == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("You have not selected an appointment to update.");
            alert.showAndWait();
        }

        else {
            UpdateAppointmentController uac = loader.getController();
            uac.sendAppointment(appointmentToUpdate);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    /**
     * Deletes the selected appointment from the appointment table.
     * @param event
     * @throws IOException
     */
    @FXML
    public void deleteAppointmentButtonHandler(ActionEvent event) throws IOException, SQLException {

        appointmentToDelete = appointmentTableView.getSelectionModel().getSelectedItem();

        if (appointmentToDelete == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("You have not selected an appointment to delete.");
            alert.showAndWait();
        }

        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to delete Appointment ID: " +
                    appointmentToDelete.getAppointmentId() + " - Type: " + appointmentToDelete.getType() + "?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                AppointmentDB.deleteAppointment(appointmentToDelete.getAppointmentId());
            }
            appointmentTableView.setItems(AppointmentDB.getAllAppointments());
        }
    }


    /**
     * Exits the program when the Exit button is clicked.
     * @param event
     */
    @FXML
    public void exitButtonHandler(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to exit the program? Any unsaved changes will be lost.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentTableView.setItems(AppointmentDB.getAllAppointments());
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        contactIdColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));



    }
}
