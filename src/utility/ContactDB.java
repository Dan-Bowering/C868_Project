package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDB {

    /**
     * Query to return the contact ID.
     * @param contact
     * @return
     * @throws SQLException
     */
    public static int getContactId(String contact) throws SQLException {

        int contactId = 0;

        // SQL query and execute to a result set
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
     * Query to return a list of all contact names from the DB.
     * @return allContactNames
     * @throws SQLException
     */
    public static ObservableList<String> getAllContactNames() throws SQLException {

        ObservableList<String> allContactNames = FXCollections.observableArrayList();

        // SQL query and execute to a result set
        String sql = "SELECT Contact_Name FROM contacts";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        // Add result set to the list
        while (rs.next()) {
            allContactNames.add(rs.getString("Contact_Name"));
        }
        return allContactNames;
    }
}
