package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDB {

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
}
