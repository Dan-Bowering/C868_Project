package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class UserDB {

    public static User currentUser;
    public static ZoneId userTimeZone;
    public static LocalDateTime userCurrentTime;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static ZoneId getUserTimeZone() {
        return userTimeZone;
    }

    public static LocalDateTime getUserCurrentTime() {
        return userCurrentTime;
    }

    public static ObservableList<User> getAllUsers(){
        ObservableList<User> allUsers = FXCollections.observableArrayList();

        try {
            String sql = "SELECT User_ID, User_name FROM users";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int userId = rs.getInt("User_ID");
                String username = rs.getString("User_name");

                User u = new User(userId, username);
                allUsers.add(u);
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }

        return allUsers;
    }

    public static boolean validateLogin(String usernameInput, String passwordInput) throws SQLException {

        try {
            String sql = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, usernameInput);
            ps.setString(2, passwordInput);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                currentUser = new User(rs.getInt("User_ID"), rs.getString("User_Name"));
                userTimeZone = ZoneId.systemDefault();
                userCurrentTime = LocalDateTime.now();
                ps.close();
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }


}
