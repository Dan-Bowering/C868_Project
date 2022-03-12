package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.ReportOne;
import model.ReportThree;
import model.ReportTwo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentDB {

    /**
     * Query returns all appointments from appointments table.
     * @return allAppointments
     */
    public static ObservableList<Appointment> getAllAppointments() {

        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        // SQL query and execute to a result set
        try {
            String sql = "SELECT * FROM appointments as a LEFT OUTER JOIN contacts as c ON a.Contact_ID = c.Contact_ID;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Get data to use in constructor
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

                // Add new Appointment object with data from query
                Appointment a = new Appointment(appointmentId, title, description, location, type, start, end,
                        createDate, createBy, lastUpdateTime, lastUpdateBy, customerId, userId, contactId, contactName);

                allAppointments.add(a);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return allAppointments;
    }

    /**
     * Query returning all appointments occurring in current month.
     * @param currentMonth
     * @param oneMonthOut
     * @return allMonthlyAppointments
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAllMonthlyAppointments(ZonedDateTime currentMonth,
                                                                        ZonedDateTime oneMonthOut) throws SQLException {

        ObservableList<Appointment> allMonthlyAppointments = FXCollections.observableArrayList();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Set the range for the current month and format time
        String monthStart = currentMonth.format(timeFormat);
        String monthEnd = oneMonthOut.format(timeFormat);

            // SQL query and execute to a result set
            String sql = "SELECT * FROM appointments as a LEFT OUTER JOIN contacts as c ON a.Contact_ID = c.Contact_ID " +
                    "WHERE Start Between ? AND ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, monthStart);
            ps.setString(2, monthEnd);
            ResultSet rs = ps.executeQuery();

            // Get data to use in constructor
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

                // Add new Appointment object with data from query
                Appointment a = new Appointment(appointmentId, title, description, location, type, start, end,
                        createDate, createBy, lastUpdateTime, lastUpdateBy, customerId, userId, contactId, contactName);

                allMonthlyAppointments.add(a);
            }
        return allMonthlyAppointments;
    }

    /**
     * Query returning all appointments occurring in current week.
     * @param currentDay
     * @param sevenDaysOut
     * @return allWeeklyAppointments
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAllWeeklyAppointments(ZonedDateTime currentDay,
                                                                       ZonedDateTime sevenDaysOut) throws SQLException {

        ObservableList<Appointment> allWeeklyAppointments = FXCollections.observableArrayList();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Set the range for the current week and format time
        String dayStart = currentDay.format(timeFormat);
        String dayEnd = sevenDaysOut.format(timeFormat);

        // SQL query and execute to a result set
        String sql = "SELECT * FROM appointments as a LEFT OUTER JOIN contacts as c ON a.Contact_ID = c.Contact_ID " +
                "WHERE Start Between ? AND ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, dayStart);
        ps.setString(2, dayEnd);
        ResultSet rs = ps.executeQuery();

        // Get data to use in constructor
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

            // Add new Appointment object with data from query
            Appointment a = new Appointment(appointmentId, title, description, location, type, start, end,
                    createDate, createBy, lastUpdateTime, lastUpdateBy, customerId, userId, contactId, contactName);

            allWeeklyAppointments.add(a);
        }
        return allWeeklyAppointments;
    }

    /**
     * Inserts an appointment to the DB.
     * @param title
     * @param description
     * @param location
     * @param type
     * @param utcZoneStart
     * @param utcZoneEnd
     * @param customerId
     * @param contactId
     * @throws SQLException
     */
    public static void addAppointment(String title, String description, String location, String type,
                                      ZonedDateTime utcZoneStart, ZonedDateTime utcZoneEnd, int customerId,
                                      int contactId, int userId) throws SQLException {

        // SQL query, format time for input to match DB, and execute
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
            ps.setInt(12, userId);
            ps.setInt(13, contactId);

            ps.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Updates an appointment in the DB.
     * @param appointmentId
     * @param title
     * @param description
     * @param location
     * @param type
     * @param utcZoneStart
     * @param utcZoneEnd
     * @param customerId
     * @param contactId
     * @throws SQLException
     */
    public static void updateAppointment(int appointmentId, String title, String description, String location, String type,
                                         ZonedDateTime utcZoneStart, ZonedDateTime utcZoneEnd, int customerId,
                                         int contactId, int userId) throws SQLException {

        // SQL query, format time for input to match DB, and execute
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
            ps.setInt(10, userId);
            ps.setInt(11, contactId);
            ps.setInt(12, appointmentId);

            ps.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Deletes an appointment from the DB.
     * @param appointmentId
     * @return boolean
     */
    public static Boolean deleteAppointment(int appointmentId) {

        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID=?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, appointmentId);
            ps.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes associated appointments from DB (when a customer is deleted).
     * @param customerId
     * @return boolean
     * @throws SQLException
     */
    public static Boolean deleteAssociatedAppointments(int customerId) throws SQLException {

        try {
            String sql = "DELETE FROM appointments WHERE Customer_ID=?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.execute();
            ps.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @param customerId
     * @param startLocalDateTime
     * @param endLocalDateTime
     * @return boolean
     * @throws SQLException
     */
    public static boolean overlappingAppointments(int customerId, LocalDateTime startLocalDateTime,
                                                  LocalDateTime endLocalDateTime) throws SQLException {

        // SQL query and execute to a result set
        try {
            String sql = "SELECT Start, End FROM appointments WHERE Customer_ID=?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            // Sets the proposed times accordingly for comparison
            while (rs.next()) {
                Timestamp tsStart = rs.getTimestamp("Start");
                Timestamp tsEnd = rs.getTimestamp("End");
                LocalDateTime ldtStart = tsStart.toLocalDateTime();
                LocalDateTime ldtEnd = tsEnd.toLocalDateTime();

                // Checks against existing appointments for overlap
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

    /**
     * Checks logged in user's schedule for appointments within 15 minutes of current time.
     * @return appointmentsIn15Minutes
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppointmentsIn15Minutes() throws SQLException {

        // Setup DateTimeFormatter and get current 15 minute range
        ObservableList<Appointment> appointmentsIn15Minutes = FXCollections.observableArrayList();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Timestamp checkStart = Timestamp.valueOf(LocalDateTime.now().format(timeFormat));
        Timestamp checkEnd = Timestamp.valueOf(LocalDateTime.now().plusMinutes(15).format(timeFormat));

        // SQL query and execute to a result set
        try {
            String sql = "SELECT * FROM appointments as a LEFT OUTER JOIN contacts as c ON a.Contact_ID = c.Contact_ID " +
                    "WHERE START BETWEEN ? AND ? AND User_ID=?;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setTimestamp(1, checkStart);
            ps.setTimestamp(2, checkEnd);
            ps.setInt(3, UserDB.getCurrentUser().getUserId());
            ResultSet rs = ps.executeQuery();

            // Get data to use in constructor
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

                // Add new Appointment object with data from query
                Appointment a = new Appointment(appointmentId, title, description, location, type, start, end,
                        createDate, createBy, lastUpdateTime, lastUpdateBy, customerId, userId, contactId, contactName);

                appointmentsIn15Minutes.add(a);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return appointmentsIn15Minutes;
    }

    /**
     * Query for appointments by Type and Month report.
     * @return appointmentList
     * @throws SQLException
     */
    public static ObservableList<ReportOne> appointmentsByTypeAndMonth() throws SQLException {

        ObservableList<ReportOne> reportList = FXCollections.observableArrayList();

        // SQL query and execute to a result set
        String sql = "SELECT Type, monthname(Start) as 'Month', count(Type) as 'Type Total' " +
                    "FROM appointments group by Type order by monthname(Start);";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        // Add query results to list in a string for report display
        while(rs.next()) {
            String type = rs.getString("Type");
            String month = rs.getString("Month");
            int total = rs.getInt("Type Total");

            // Add new Appointment object with data from query
            ReportOne r = new ReportOne(type, month, total);

            reportList.add(r);
        }

        return reportList;
    }


    /**
     * Query to return all appointments by contact ID.
     * @return
     * @throws SQLException
     */
    public static ObservableList<ReportTwo> appointmentsByContactId() throws SQLException {

        ObservableList<ReportTwo> reportList = FXCollections.observableArrayList();

        // SQL query and execute to a result set
        String sql = "SELECT Appointment_ID, Title, Type, Description, Start, End, Customer_ID, Contact_ID " +
                "FROM appointments ORDER BY Contact_ID;";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        // Add query results to list in a string for report display
        while(rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            int contactId = rs.getInt("Contact_ID");

            // Add new Appointment object with data from query
            ReportTwo r = new ReportTwo(appointmentId, type, start, end, contactId);

            reportList.add(r);

        }
        return reportList;
    }

    /**
     * Queries all customers in the DB who do not have an appointment scheduled.  This is my
     * 3rd custom-generated report.
     * @return followupList
     * @throws SQLException
     */
    public static ObservableList<ReportThree> studentsNeedFollowUp() throws SQLException {

        ObservableList<ReportThree> reportList = FXCollections.observableArrayList();

        // SQL query and execute to a result set
        String sql = "SELECT customers.Customer_ID, customers.Customer_Name, Phone, customers.Student_ID, Instructor_ID " +
                "FROM customers LEFT JOIN appointments ON customers.Customer_ID = appointments.Customer_ID WHERE " +
                "appointments.Customer_ID IS NULL AND Instructor_ID IS NULL";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        // Add query results to list in a string for report display
        while(rs.next()) {
            int studentId = rs.getInt("Student_ID");
            String name = rs.getString("Customer_Name");
            String phone = rs.getString("Phone");

            // Add new Appointment object with data from query
            ReportThree r = new ReportThree(studentId, name, phone);

            reportList.add(r);
        }
        return reportList;
    }

}
