package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentDB {

    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments as a LEFT OUTER JOIN contacts as c ON a.Contact_ID = c.Contact_ID;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createBy = rs.getString("Created_By");
                Timestamp lastUpdateTime = rs.getTimestamp("Last_Update");
                String lastUpdateBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                Appointment a = new Appointment(appointmentId, title, description, location, type, start, end,
                        createDate, createBy, lastUpdateTime, lastUpdateBy, customerId, userId, contactId, contactName);

                allAppointments.add(a);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return allAppointments;
    }

    public static ObservableList<Appointment> getAllMonthlyAppointments(ZonedDateTime currentMonth,
                                                                        ZonedDateTime oneMonthOut) throws SQLException {

        ObservableList<Appointment> allMonthlyAppointments = FXCollections.observableArrayList();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String monthStart = currentMonth.format(timeFormat);
        String monthEnd = oneMonthOut.format(timeFormat);

            String sql = "SELECT * FROM appointments as a LEFT OUTER JOIN contacts as c ON a.Contact_ID = c.Contact_ID " +
                    "WHERE Start Between ? AND ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, monthStart);
            ps.setString(2, monthEnd);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createBy = rs.getString("Created_By");
                Timestamp lastUpdateTime = rs.getTimestamp("Last_Update");
                String lastUpdateBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                Appointment a = new Appointment(appointmentId, title, description, location, type, start, end,
                        createDate, createBy, lastUpdateTime, lastUpdateBy, customerId, userId, contactId, contactName);

                allMonthlyAppointments.add(a);
            }
        return allMonthlyAppointments;
    }

    public static ObservableList<Appointment> getAllWeeklyAppointments(ZonedDateTime currentDay,
                                                                        ZonedDateTime sevenDaysOut) throws SQLException {

        ObservableList<Appointment> allWeeklyAppointments = FXCollections.observableArrayList();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String dayStart = currentDay.format(timeFormat);
        String dayEnd = sevenDaysOut.format(timeFormat);

        String sql = "SELECT * FROM appointments as a LEFT OUTER JOIN contacts as c ON a.Contact_ID = c.Contact_ID " +
                "WHERE Start Between ? AND ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, dayStart);
        ps.setString(2, dayEnd);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createBy = rs.getString("Created_By");
            Timestamp lastUpdateTime = rs.getTimestamp("Last_Update");
            String lastUpdateBy = rs.getString("Last_Updated_By");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");

            Appointment a = new Appointment(appointmentId, title, description, location, type, start, end,
                    createDate, createBy, lastUpdateTime, lastUpdateBy, customerId, userId, contactId, contactName);

            allWeeklyAppointments.add(a);
        }
        return allWeeklyAppointments;
    }

    public static void addAppointment(String title, String description, String location, String type,
                                      ZonedDateTime utcZoneStart, ZonedDateTime utcZoneEnd, int customerId,
                                      int contactId) throws SQLException {

        try {
            String sql = "INSERT INTO appointments VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setString(5, utcZoneStart.format(timeFormat));
            ps.setString(6, utcZoneEnd.format(timeFormat));
            ps.setString(7, ZonedDateTime.now(ZoneOffset.UTC).format(timeFormat));
            ps.setString(8, UserDB.getCurrentUser().getUsername());
            ps.setString(9, ZonedDateTime.now(ZoneOffset.UTC).format(timeFormat));
            ps.setString(10, UserDB.getCurrentUser().getUsername());
            ps.setInt(11, customerId);
            ps.setInt(12, UserDB.getCurrentUser().getUserId());
            ps.setInt(13, contactId);

            ps.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void updateAppointment(int appointmentId, String title, String description, String location, String type,
                                         ZonedDateTime utcZoneStart, ZonedDateTime utcZoneEnd, int customerId,
                                         int contactId) throws SQLException {

        try {
            String sql = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, " +
                    "End=?, Last_Update=?, Last_Updated_By=?, Customer_ID=?, User_ID=?, Contact_ID=? WHERE " +
                    "Appointment_ID=?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setString(5, utcZoneStart.format(timeFormat));
            ps.setString(6, utcZoneEnd.format(timeFormat));
            ps.setString(7, ZonedDateTime.now(ZoneOffset.UTC).format(timeFormat));
            ps.setString(8, UserDB.getCurrentUser().getUsername());
            ps.setInt(9, customerId);
            ps.setInt(10, UserDB.getCurrentUser().getUserId());
            ps.setInt(11, contactId);
            ps.setInt(12, appointmentId);

            ps.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static Boolean deleteAppointment(int appointmentId) throws SQLException {

        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID=?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, appointmentId);
            ps.execute();
            ps.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean overlappingAppointments(int customerId, LocalDateTime startLocalDateTime,
                                                  LocalDateTime endLocalDateTime) throws SQLException {

        try {
            String sql = "SELECT Start, End FROM appointments WHERE Customer_ID=?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Timestamp tsStart = rs.getTimestamp("Start");
                Timestamp tsEnd = rs.getTimestamp("End");
                LocalDateTime ldtStart = tsStart.toLocalDateTime();
                LocalDateTime ldtEnd = tsEnd.toLocalDateTime();

                if (startLocalDateTime.isAfter(ldtStart) && startLocalDateTime.isBefore(ldtEnd) ||
                        (endLocalDateTime.isAfter(ldtStart) && endLocalDateTime.isBefore(ldtEnd)) ||
                        (startLocalDateTime.isBefore(ldtStart) && endLocalDateTime.isAfter(ldtEnd)))
                {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static ObservableList<Appointment> getAppointmentsIn15Minutes() throws SQLException {

        ObservableList<Appointment> appointmentsIn15Minutes = FXCollections.observableArrayList();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Timestamp checkStart = Timestamp.valueOf(LocalDateTime.now().format(timeFormat));
        Timestamp checkEnd = Timestamp.valueOf(LocalDateTime.now().plusMinutes(15).format(timeFormat));

        try {
            String sql = "SELECT * FROM appointments as a LEFT OUTER JOIN contacts as c ON a.Contact_ID = c.Contact_ID " +
                    "WHERE START BETWEEN ? AND ? AND User_ID=?;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setTimestamp(1, checkStart);
            ps.setTimestamp(2, checkEnd);
            ps.setInt(3, UserDB.getCurrentUser().getUserId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createBy = rs.getString("Created_By");
                Timestamp lastUpdateTime = rs.getTimestamp("Last_Update");
                String lastUpdateBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                Appointment a = new Appointment(appointmentId, title, description, location, type, start, end,
                        createDate, createBy, lastUpdateTime, lastUpdateBy, customerId, userId, contactId, contactName);

                appointmentsIn15Minutes.add(a);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return appointmentsIn15Minutes;
    }

    public static ObservableList<String> appointmentsByTypeAndMonth() throws SQLException {

        ObservableList<String> appointmentList = FXCollections.observableArrayList();

            String sql = "SELECT Type, monthname(Start) as 'Month', count(Type) as 'Type Total' " +
                    "FROM appointments group by Type order by monthname(Start);";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
               appointmentList.add("Type: " + rs.getString("Type") + "  |  Month: " +
                       rs.getString("Month") + "  |  Type Total: " + rs.getString("Type Total") +
                       "\n");
            }
            return appointmentList;
    }

    public static ObservableList<String> appointmentsByContactId() throws SQLException {

        ObservableList<String> appointmentByContactList = FXCollections.observableArrayList();

        String sql = "SELECT Appointment_ID, Title, Type, Description, Start, End, Customer_ID, Contact_ID " +
                "FROM appointments ORDER BY Contact_ID;";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            appointmentByContactList.add("Appointment ID: " + rs.getString("Appointment_ID") +
                                        " | Title: " + rs.getString("Title") +
                                        " | Type: " + rs.getString("Type") +
                                        " | Description: " + rs.getString("Description") +
                                        " | Start: " + rs.getString("Start") +
                                        " | End: " + rs.getString("End") +
                                        " | Customer ID: " + rs.getString("Customer_ID") +
                                        " | Contact ID: " + rs.getString("Contact_ID") + "\n");
        }
        return appointmentByContactList;
    }

}
