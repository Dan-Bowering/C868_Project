package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDB {

    /**
     * Gets a list of all all customers from the DB.
     * @return allCustomers
     */
    public static ObservableList<Customer> getAllCustomers() {

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
     * Gets a list of all countries from the DB.
     * @return allCountries
     * @throws SQLException
     */
    public static ObservableList<String> getAllCountries() throws SQLException {

        ObservableList<String> allCountries = FXCollections.observableArrayList();

        String sql = "SELECT DISTINCT Country FROM countries";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            allCountries.add(rs.getString("Country"));
        }
        return allCountries;
    }

    /**
     * Gets a list of all divisions from the DB based on the country ID.
     * @return usDivisions
     * @throws SQLException
     */
    public static ObservableList<String> getUsDivisions() throws SQLException {

        ObservableList<String> usDivisions = FXCollections.observableArrayList();

        String sql = "SELECT Division FROM first_level_divisions WHERE Country_ID = 1";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            usDivisions.add(rs.getString("Division"));
        }

        return usDivisions;
    }

    /**
     * Adds a customer to the customers table in the DB.
     *
     */

}
