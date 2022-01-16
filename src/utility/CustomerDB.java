package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDB {

    public static ObservableList<Customer> getAllCustomers(){
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        try {
            String sql = "SELECT c.Customer_ID, c.Customer_Name, c.Address, c.Postal_Code, c.Phone, c.Division_ID, " +
                    "f.Division, f.Country_ID, co.Country FROM customers as c INNER JOIN first_level_divisions " +
                    "as f on c.Division_ID = f.Division_ID INNER JOIN countries as co ON f.Country_ID = co.Country_ID";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                String country = rs.getString("Country");

                Customer c = new Customer(customerId, customerName, address, postalCode, phone, divisionId, division, countryId, country);
                allCustomers.add(c);
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }

        return allCustomers;
    }

    /**
     * Adds a customer to the customers table in the DB.
     *
     */
    public static void addCustomer(Customer customerToAdd) {

    }
}
