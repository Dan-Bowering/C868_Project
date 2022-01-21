package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentDB {

    public static ObservableList<Appointment> getAllAppointments(){
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, " +
                    "Contact_ID FROM appointments";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                String start = rs.getString("Start");
                String end = rs.getString("End");
                int customerId = rs.getInt("Customer_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointment a = new Appointment(appointmentId, title, description, location, type, start, end, customerId, contactId);
                allAppointments.add(a);
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }

        return allAppointments;
    }

    public static void addAppointment(String title, String description, String location, String type,
                                      LocalDateTime startDateTime, LocalDateTime endDateTime, int customerId,
                                      int contactId) throws SQLException {

        try {
            String sql = "INSERT INTO customers VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setString(5, String.valueOf(startDateTime));
            ps.setString(6, String.valueOf(endDateTime));
            ps.setString(7, ZonedDateTime.now(ZoneOffset.UTC).format(timeFormat));
            ps.setString(8, UserDB.getCurrentUser().getUsername());
            ps.setString(9, ZonedDateTime.now(ZoneOffset.UTC).format(timeFormat));
            ps.setString(10, UserDB.getCurrentUser().getUsername());
            ps.setInt(11, customerId);
            ps.setInt(12, UserDB.getCurrentUser().getUserId());
            ps.setInt(13, contactId);

            ps.execute();

        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}
