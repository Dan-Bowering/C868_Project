package controller;

import javafx.collections.FXCollections;
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
    @FXML RadioButton monthRadioButton;
    @FXML RadioButton weekRadioButton;
    @FXML RadioButton allRadioButton;
    @FXML TextField searchTextField;


    public static Appointment appointmentToUpdate = null;
    public static Appointment appointmentToDelete = null;

    public static Appointment getAppointmentToUpdate() {
        return appointmentToUpdate;
    }

    /**
     * Performs the appointment search when the search button is clicked.
     * @param actionEvent
     */
    @FXML
    public void searchButtonHandler (ActionEvent actionEvent) {

        String appointmentTitleSearched = searchTextField.getText();
        ObservableList<Appointment> appointmentSearched = lookupAppointmentTitle(appointmentTitleSearched);

        try {

            if (appointmentSearched.size() == 0) {
                int appointmentId = Integer.parseInt(appointmentTitleSearched);
                Appointment appointment = lookupAppointmentId(appointmentId);
                if (appointment != null)
                    appointmentSearched.add(appointment);
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("INFORMATION");
                    alert.setContentText("Your search criteria did not match an existing appointments. Please try again.");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.get() == ButtonType.OK) {
                        searchTextField.setText("");
                    }
                }
            }
            appointmentTableView.setItems(appointmentSearched);
            searchTextField.setText("");
        }
        catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFORMATION");
            alert.setContentText("Your search criteria did not match an existing appointments. Please try again.");
            alert.showAndWait();
            searchTextField.setText("");
        }
    }

    /**
     * Loops through the Appointments list to perform a text-based search for a matching
     * appointment title.
     * @param appointmentTitleSearched
     * @return appointmentFound
     */
    private ObservableList<Appointment> lookupAppointmentTitle (String appointmentTitleSearched) {

        ObservableList<Appointment> appointmentFound = FXCollections.observableArrayList();
        ObservableList<Appointment> allAppointments = AppointmentDB.getAllAppointments();

        for (Appointment appointment : allAppointments) {
            if (appointment.getTitle().contains(appointmentTitleSearched)) {
                appointmentFound.add(appointment);
            }
        }
        return appointmentFound;
    }

    /**
     * Loops through the Appointments list to perform an integer-based search for matching
     * appointment ID.
     * @param appointmentId
     * @return appointment
     * @return null
     */
    private Appointment lookupAppointmentId (int appointmentId) {

        ObservableList<Appointment> allAppointments = AppointmentDB.getAllAppointments();

        for (Appointment appointment : allAppointments) {
            if (appointment.getAppointmentId() == appointmentId) {
                return appointment;
            }
        }
        return null;
    }

    /**
     * Switches to the Customer screen when "view customer list" button is clicked.
     * @param event
     * @throws IOException
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

    /**
     * Switches to the Add Appointment form when the button "Add Appointments" is clicked.
     * @param event
     * @throws IOException
     */
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
     * Switches to the Reports screen when the button "Reports" is clicked.
     * @param event
     * @throws IOException
     */
    public void reportsButtonHandler(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportsScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Reports");
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

        // Get the selected appointment
        appointmentToUpdate = appointmentTableView.getSelectionModel().getSelectedItem();

        // Display an error if no selection is made
        if (appointmentToUpdate == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("You have not selected an appointment to update.");
            alert.showAndWait();
        }

        // Take selected appointment and pass data to Update Appointment form
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
     * @throws SQLException
     */
    @FXML
    public void deleteAppointmentButtonHandler(ActionEvent event) throws SQLException {

        // Get the selected appointment
        appointmentToDelete = appointmentTableView.getSelectionModel().getSelectedItem();

        // Displays error message if no selection is made
        if (appointmentToDelete == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("You have not selected an appointment to delete.");
            alert.showAndWait();
        }

        // Custom confirmation alert indicating what appointment will be deleted
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to delete Appointment ID: " +
                    appointmentToDelete.getAppointmentId() + " - Type: " + appointmentToDelete.getType() + "?");

            Optional<ButtonType> result = alert.showAndWait();

            // Deletes appointment from DB and updates the tableview
            if (result.get() == ButtonType.OK) {
                AppointmentDB.deleteAppointment(appointmentToDelete.getAppointmentId());
            }
            appointmentTableView.setItems(AppointmentDB.getAllAppointments());
        }
    }

    /**
     * Exits the program when the Exit button is clicked.  I used Lambda
     * expression to simplify the code required to handle the alert displayed
     * when exiting the program.
     * @param event
     */
    @FXML
    public void exitButtonHandler(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to exit the program? Any unsaved changes will be lost.");

        // Lambda expression used to simplify the alert response for exiting the program
        alert.showAndWait().ifPresent((result) -> {
            if (result == ButtonType.OK) {
                System.exit(0);
            }
        });
    }

    /**
     * Updates the tableview to display appointments for the month
     * if the "current month" radio button is selected.
     * @param event
     * @throws SQLException
     */
    @FXML
    public void monthRadioButtonHandler(ActionEvent event) throws SQLException {

        ZonedDateTime currentMonth = ZonedDateTime.now();
        ZonedDateTime oneMonthOut = ZonedDateTime.now().plusMonths(1);

        if(monthRadioButton.isSelected()) {
            appointmentTableView.setItems(AppointmentDB.getAllMonthlyAppointments(currentMonth, oneMonthOut));
        }
    }

    /**
     * Updates the tableview to display appointments for the week
     * if the "current week" radio button is selected.
     * @param event
     * @throws SQLException
     */
    @FXML
    public void weekRadioButtonHandler(ActionEvent event) throws SQLException {

        ZonedDateTime currentDay = ZonedDateTime.now();
        ZonedDateTime sevenDaysOut = ZonedDateTime.now().plusDays(7);

        if(weekRadioButton.isSelected()) {
            appointmentTableView.setItems(AppointmentDB.getAllMonthlyAppointments(currentDay, sevenDaysOut));
        }
    }

    /**
     * Updates the tableview to display all appointments if the "all appointments"
     * radio button is selected.
     * @param event
     */
    @FXML
    public void allAppointmentsRadioButtonHandler(ActionEvent event) {

            if(allRadioButton.isSelected()) {
                appointmentTableView.setItems(AppointmentDB.getAllAppointments());
            }
    }

    /**
     * Logs the user out of the application and send them back to the Login Form. I used Lambda
     * expression to simplify the code required to handle the alert displayed when logging out.
     * @param event
     */
    @FXML
    public void logoutButtonHandler(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to logout?");

        // Lambda expression used to simplify the alert response to logging out of the program
        alert.showAndWait().ifPresent((result) -> {
            if (result == ButtonType.OK) {
                Parent main = null;
                try {
                    main = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
                    Scene scene = new Scene(main);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Set the appointments tableview
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
