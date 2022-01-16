package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import utility.CustomerDB;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.lang.String.valueOf;

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
    @FXML ComboBox countryComboBox;
    @FXML ComboBox stateProvinceComboBox;
    @FXML TextField phoneTextField;


    private static Customer customerToUpdate = null;

    /**
     * customerToUpdate getter.
     * @return customerToUpdate
     */
    public static Customer customerToUpdate() {
        return customerToUpdate;
    }

    /**
     * Saves the customer data entered in the editable fields when
     * the Save button is clicked.
     * @param event
     * @throws IOException
     */
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
            countryComboBox.setPromptText(String.valueOf(customerToUpdate().getCountry()));
            stateProvinceComboBox.setPromptText(String.valueOf(customerToUpdate().getDivision()));
            phoneTextField.setText(String.valueOf(customerToUpdate().getPhone()));
        }
    }



    /**
     * Exits the program when the Exit button is clicked.
     * @param event
     */
    @FXML
    void exitButtonHandler(ActionEvent event) {
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
        customerTableView.setItems(CustomerDB.getAllCustomers());
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        stateProvinceColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

    }
}
