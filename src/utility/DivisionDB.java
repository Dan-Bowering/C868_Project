package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionDB {

    public static ObservableList<String> getAllUSDivisions() throws SQLException {

        ObservableList<String> allUSDivisions = FXCollections.observableArrayList();

        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 1";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            allUSDivisions.add(rs.getString("Division"));
        }
        return allUSDivisions;
    }

    public static ObservableList<String> getAllUKDivisions() throws SQLException {

        ObservableList<String> allUKDivisions = FXCollections.observableArrayList();

        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 2";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            allUKDivisions.add(rs.getString("Division"));
        }
        return allUKDivisions;
    }

    public static ObservableList<String> getAllCanadaDivisions() throws SQLException {

        ObservableList<String> allCanadaDivisions = FXCollections.observableArrayList();

        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = 3";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            allCanadaDivisions.add(rs.getString("Division"));
        }
        return allCanadaDivisions;
    }
/* This is not pulling the division ID for some reason...
    public static int getDivisionId(String division) throws SQLException {

        String sql = "SELECT Division, Division_ID FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, division);
        ResultSet rs = ps.executeQuery();

        int divisionId = 0;
        while (rs.next()) {
            divisionId = rs.getInt("Division_ID");
        }
        return divisionId;
    }

*/
}



