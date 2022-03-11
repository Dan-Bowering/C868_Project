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
import model.CourseInstructor;
import model.Student;
import utility.CountryDB;
import utility.CustomerDB;
import utility.DivisionDB;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    @FXML TextField customerIdTextField;
    @FXML ComboBox<String> studentInstructorComboBox;
    @FXML TextField customerNameTextField;
    @FXML TextField addressTextField;
    @FXML TextField postalCodeTextField;
    @FXML ComboBox<String> countryComboBox;
    @FXML ComboBox<String> divisionComboBox;
    @FXML TextField phoneTextField;
    @FXML Label studentInstructorLabel;
    @FXML RadioButton studentRadioButton;
    @FXML RadioButton instructorRadioButton;
    @FXML ToggleGroup addCustomerToggleGroup;

    private boolean isStudent;

    /**
     * Saves the newly added customer information and updates the table view.
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    public void saveAddCustomerHandler(ActionEvent event) throws SQLException, IOException {

        try {
            // Get the input
            String studentInstructorId = studentInstructorComboBox.getValue();
            String customerName = customerNameTextField.getText();
            String address = addressTextField.getText();
            String postalCode = postalCodeTextField.getText();
            String phone = phoneTextField.getText();
            String country = countryComboBox.getValue();
            String division = divisionComboBox.getValue();

            // Validate no fields are empty
            if (customerNameTextField.getText().isEmpty() || addressTextField.getText().isEmpty() ||
                    postalCodeTextField.getText().isEmpty() || phoneTextField.getText().isEmpty() ||
                    countryComboBox.getValue().isEmpty() || divisionComboBox.getValue().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("Please enter valid values in each field.");
                alert.showAndWait();
                addNewCustomerButtonHandler(event);
            }

            // Add to the DB if a student is being added
            else if (studentRadioButton.isSelected()) {
                CustomerDB.addStudent(customerName, address, postalCode, phone, country,
                        DivisionDB.getDivisionId(division), CustomerDB.getStudentId(studentInstructorId));

                // Add to the DB if an instructor is being added
            } else if (instructorRadioButton.isSelected()) {
                CustomerDB.addInstructor(customerName, address, postalCode, phone, country,
                        DivisionDB.getDivisionId(division), CustomerDB.getInstructorId(studentInstructorId));
            }

            // Set the stage to refresh the tableview
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
            Scene scene = new Scene(root, 1000, 520);
            stage.setTitle("Main Screen");
            stage.setScene(scene);
            stage.show();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please enter valid values in each field.");
            alert.showAndWait();
            addNewCustomerButtonHandler(event);
        }
    }

    /**
     * Sets the ID field according to which radio button is selected.
     * @param event
     * @throws IOException
     */
    public void instructorIsSelected(ActionEvent event) throws IOException, SQLException {

        isStudent = false;
        studentInstructorLabel.setText("Instructor Program");
        studentInstructorComboBox.setItems(CustomerDB.getAllInstructors());
    }

    /**
     * Sets the ID field according to which radio button is selected.
     * @param event
     * @throws IOException
     */
    public void studentIsSelected(ActionEvent event) throws IOException, SQLException {

        isStudent = true;
        studentInstructorLabel.setText("Student Program");
        studentInstructorComboBox.setItems(CustomerDB.getAllStudents());
    }

    /**
     * Determines which division list to populate based on which country is selected.
     * @param event
     * @throws SQLException
     */
    @FXML
    public void countrySelected(ActionEvent event) throws SQLException {

        if (countryComboBox.getSelectionModel().getSelectedItem().equals("U.S")) {
            divisionComboBox.setItems(DivisionDB.getAllUSDivisions());
        }
        else if (countryComboBox.getSelectionModel().getSelectedItem().equals("UK")) {
            divisionComboBox.setItems(DivisionDB.getAllUKDivisions());
        }
        else if (countryComboBox.getSelectionModel().getSelectedItem().equals("Canada")) {
            divisionComboBox.setItems(DivisionDB.getAllCanadaDivisions());
        }
    }

    /**
     * Navigates back to the main Appointments table without saving changes.
     * @param event
     * @throws IOException
     */
    @FXML
    public void cancelButtonHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Are you sure you want to go back?  All changes will be lost.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            toCustomerScreen(event);
        }
    }

    /**
     * Returns to the Customer screen.
     * @param event
     * @throws IOException
     */
    @FXML
    public void toCustomerScreen(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Customer List");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Navigates to the Add Customer form when the "Add New Customer"
     * button is clicked.
     * @param event
     * @throws IOException
     */
    @FXML
    public void addNewCustomerButtonHandler(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomerForm.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Set all countries in the ComboBox
        try {
            countryComboBox.setItems(CountryDB.getAllCountries());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
