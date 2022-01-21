package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDB {

    public static int getContactId(String contact) throws SQLException {

        int contactId = 0;

        String sql = "SELECT Contact_ID, Contact_Name FROM contacts WHERE Contact_Name = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, contact);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            contactId = rs.getInt("Contact_ID");
        }
        return contactId;
    }

    /**
     * Gets a list of all countries from the DB.
     * @return allCountries
     * @throws SQLException
     */
    public static ObservableList<String> getAllContactNames() throws SQLException {

        ObservableList<String> allContactNames = FXCollections.observableArrayList();

        String sql = "SELECT Contact_Name FROM contacts";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            allContactNames.add(rs.getString("Contact_Name"));
        }
        return allContactNames;
    }
}
