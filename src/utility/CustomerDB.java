package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
                String division = rs.getString("Division");
                String country = rs.getString("Country");

                Customer c = new Customer(customerId, customerName, address, postalCode, phone, division, country);
                allCustomers.add(c);
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return allCustomers;
    }

    public static void addCustomer(String customerName, String address, String postalCode, String phone,
                                   String country, int divisionId) throws SQLException {

        try {
            String sql = "INSERT INTO customers VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setString(5, ZonedDateTime.now(ZoneOffset.UTC).format(timeFormat));
            ps.setString(6, "NULL");
            ps.setString(7, ZonedDateTime.now(ZoneOffset.UTC).format(timeFormat));
            ps.setString(8, "NULL");
            ps.setInt(9, divisionId);

            ps.execute();

        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    public static void updateCustomer(String customerName, String address, String postalCode, String phone,
                                   String country, int customerId) throws SQLException {

        try {
            String sql = "INSERT INTO customers VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setString(5, ZonedDateTime.now(ZoneOffset.UTC).format(timeFormat));
            ps.setString(6, "Dan");
            ps.setString(7, ZonedDateTime.now(ZoneOffset.UTC).format(timeFormat));
            ps.setString(8, "Dan");
            ps.setInt(9, customerId);

            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
