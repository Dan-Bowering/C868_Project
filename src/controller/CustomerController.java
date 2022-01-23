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
import model.Customer;
import utility.CountryDB;
import utility.CustomerDB;
import utility.DivisionDB;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    @FXML TableView<Customer> customerTableView;
    @FXML TableColumn<Customer, Integer> customerIdColumn;
    @FXML TableColumn<Customer, String> customerNameColumn;
    @FXML TableColumn<Customer, String> addressColumn;
    @FXML TableColumn<Customer, String> postalCodeColumn;
    @FXML TableColumn<Customer, String> stateProvinceColumn;
    @FXML TableColumn<Customer, String> countryColumn;
    @FXML TableColumn<Customer, String> phoneColumn;
    @FXML TextField customerIdTextField;
    @FXML TextField customerNameTextField;
    @FXML TextField addressTextField;
    @FXML TextField postalCodeTextField;
    @FXML ComboBox<String> countryComboBox;
    @FXML ComboBox<String> divisionComboBox;
    @FXML TextField phoneTextField;

    private static Customer customerToUpdate = null;
    private static Customer customerToDelete = null;

    public static Customer customerToUpdate() {
        return customerToUpdate;
    }

    /**
     * Saves the customer data entered in the editable fields when
     * the Save button is clicked.
     * @param event
     * @throws IOException
     */
    @FXML
    public void updateCustomerButtonHandler(ActionEvent event) throws IOException {

        customerToUpdate = customerTableView.getSelectionModel().getSelectedItem();

        if (customerToUpdate == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("You have not selected a customer to update.");
            alert.showAndWait();
        }

        else {
            customerIdTextField.setText(String.valueOf(customerToUpdate().getCustomerId()));
            customerNameTextField.setText(String.valueOf(customerToUpdate().getCustomerName()));
            addressTextField.setText(String.valueOf(customerToUpdate().getAddress()));
            postalCodeTextField.setText(String.valueOf(customerToUpdate().getPostalCode()));
            countryComboBox.setValue(String.valueOf(customerToUpdate().getCountry()));
            divisionComboBox.setValue(String.valueOf(customerToUpdate().getDivision()));
            phoneTextField.setText(String.valueOf(customerToUpdate().getPhone()));
        }
    }

    /**
     * Saves the updated customer information and updates the table view.
     * @param event
     * @throws SQLException
     */
    @FXML
    public void saveButtonHandler(ActionEvent event) throws SQLException {

        String customerName = customerNameTextField.getText();
        String address = addressTextField.getText();
        String postalCode = postalCodeTextField.getText();
        String phone = phoneTextField.getText();
        String division = divisionComboBox.getValue();
        int customerId = Integer.parseInt(customerIdTextField.getText());

        CustomerDB.updateCustomer(customerName, address, postalCode, phone, division, customerId);

        customerTableView.setItems(CustomerDB.getAllCustomers());
    }

    /**
     * Clears the customer data fields if the Clear button is clicked.
     * @param event
     */
    @FXML
    public void clearButtonHandler(ActionEvent event) throws SQLException {
        customerIdTextField.clear();
        customerNameTextField.clear();
        addressTextField.clear();
        postalCodeTextField.clear();
        countryComboBox.getSelectionModel().clearSelection();
        countryComboBox.getPromptText();
        divisionComboBox.getSelectionModel().clearSelection();
        divisionComboBox.getPromptText();
        phoneTextField.clear();
    }

    /**
     * Navigates back to the main Appointments table without saving chaanges
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

    /**
     * Navigates to the Add Customer form when the Add New Customer
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

    /**
     * Deletes a customer from the the DB/Customer List.
     * @param
     */
    @FXML
    public void deleteCustomerButtonHandler(ActionEvent event) throws SQLException {

        customerToDelete = customerTableView.getSelectionModel().getSelectedItem();

        if (customerToDelete == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("You have not selected a customer to delete.");
            alert.showAndWait();
        }

        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to delete customer " + customerToDelete.getCustomerName() +
                                "? All associated appointments will be deleted as well.");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                CustomerDB.deleteAssociatedAppointments(customerToDelete.getCustomerId());
                CustomerDB.deleteCustomer(customerToDelete.getCustomerId());
            }
            customerTableView.setItems(CustomerDB.getAllCustomers());
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerTableView.setItems(CustomerDB.getAllCustomers());
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        stateProvinceColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        try {
            countryComboBox.setItems(CountryDB.getAllCountries());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
