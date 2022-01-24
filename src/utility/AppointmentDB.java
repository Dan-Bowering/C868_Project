package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.*;
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

}
