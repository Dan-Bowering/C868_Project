package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionDB {

    /**
     * Query to return all divisions associated with the US.
     * @return allUSDivisions
     * @throws SQLException
     */
    public static ObservableList<String> getAllUSDivisions() throws SQLException {

        ObservableList<String> allUSDivisions = FXCollections.observableArrayList();

        // SQL query and execute to a result set
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 1";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        // Add result set to the list
        while (rs.next()) {
            allUSDivisions.add(rs.getString("Division"));
        }
        return allUSDivisions;
    }

    /**
     * Query to return all divisions associated with the UK.
     * @return allUKDivisions
     * @throws SQLException
     */
    public static ObservableList<String> getAllUKDivisions() throws SQLException {

        ObservableList<String> allUKDivisions = FXCollections.observableArrayList();

        // SQL query and execute to a result set
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 2";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        // Add result set to the list
        while (rs.next()) {
            allUKDivisions.add(rs.getString("Division"));
        }
        return allUKDivisions;
    }

    /**
     * Query to return all associated divisions with Canada.
     * @return allCanadaDivisions
     * @throws SQLException
     */
    public static ObservableList<String> getAllCanadaDivisions() throws SQLException {

        ObservableList<String> allCanadaDivisions = FXCollections.observableArrayList();

        // SQL query and execute to a result set
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 3";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        // Add result set to the list
        while (rs.next()) {
            allCanadaDivisions.add(rs.getString("Division"));
        }
        return allCanadaDivisions;
    }

    /**
     * Query to return a division ID.
     * @param division
     * @return divisionId
     * @throws SQLException
     */
    public static int getDivisionId(String division) throws SQLException {

        int divisionId = 0;

        // SQL query and execute to a result set
        String sql = "SELECT Division, Division_ID FROM first_level_divisions WHERE Division = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, division);
        ResultSet rs = ps.executeQuery();

        // Add to the variable and return value
        while (rs.next()) {
            divisionId = rs.getInt("Division_ID");
        }
        return divisionId;
    }

}



