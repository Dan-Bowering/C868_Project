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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.ReportOne;
import utility.AppointmentDB;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    @FXML TableView reportsTableView;
    @FXML TableColumn reportColumnOne;
    @FXML TableColumn reportColumnTwo;
    @FXML TableColumn reportColumnThree;
    @FXML TableColumn reportColumnFour;
    @FXML TableColumn reportColumnFive;
    @FXML Label reportTitleLabel;

    /**
     * Displays a report showing the total number of customer
     * appointments by type and month.
     * @param event
     * @throws SQLException
     */
    @FXML
    public void totalAppointmentsByType(ActionEvent event) throws SQLException {

        // Set the report title and column headers
        reportTitleLabel.setText("Appointments by Type and Month");
        reportColumnOne.setText("Type");
        reportColumnTwo.setText("Month");
        reportColumnThree.setText("Total");

        // Populate the report information
        reportColumnOne.setCellValueFactory(new PropertyValueFactory<>("type"));
        reportColumnTwo.setCellValueFactory(new PropertyValueFactory<>("month"));
        reportColumnThree.setCellValueFactory(new PropertyValueFactory<>("total"));
        reportsTableView.setItems(AppointmentDB.appointmentsByTypeAndMonth());
    }

    /**
     * Displays a report showing appointments for each contact in the organization.
     * @param event
     * @throws SQLException
     */
    @FXML
    public void totalAppointmentsByContact(ActionEvent event) throws SQLException {

        ObservableList<String> reportResults = AppointmentDB.appointmentsByContactId();

       // reportTextArea.setText(String.valueOf(reportResults));
    }

    /**
     * Displays a report that shows any customers who have no appointments scheduled.
     * @param event
     * @throws SQLException
     */
    @FXML
    public void customersNeedFollowUpHandler(ActionEvent event) throws SQLException {

        ObservableList<String> reportResults = AppointmentDB.customersNeedFollowUp();

      //  reportTextArea.setText(String.valueOf(reportResults));
    }

    /**
     * Navigates back to the main Appointments screen.
     * @param event
     */@FXML
    public void backToAppointmentsHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to go back?  Any unsaved changes will be lost.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            toAppointmentScreen(event);
        }
    }

    /**
     * Loads to the main appointment screen.
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
    }
}
