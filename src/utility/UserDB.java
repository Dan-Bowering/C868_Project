package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;

public class UserDB {

    private static User currentUser;
    private static ZoneId userTimeZone;
    public UserDB() {}

    public static ObservableList<User> getAllUsers(){
        ObservableList<User> allUsers = FXCollections.observableArrayList();

        try {
            String sql = "SELECT User_ID, User_name, Password FROM users";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int userId = rs.getInt("User_ID");
                String username = rs.getString("User_name");
                String password = rs.getString("Password");

                User u = new User(userId, username, password);
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
                currentUser = new User(rs.getInt("User_ID"), rs.getString("User_Name"),
                        rs.getString("Password"));
                userTimeZone = ZoneId.systemDefault();
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

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        UserDB.currentUser = currentUser;
    }

    public static ZoneId getUserTimeZone() {
        return userTimeZone;
    }

    public static void setUserTimeZone(ZoneId userTimeZone) {
        UserDB.userTimeZone = userTimeZone;
    }
}
