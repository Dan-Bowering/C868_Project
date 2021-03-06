package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public class CustomerDB {

    /**
     * Gets a list of all customers from the DB.
     *
     * @return allCustomers
     */
    public static ObservableList<Customer> getAllCustomers() {

        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        // SQL query and execute to a result set
        try {
            String sql = "SELECT c.Customer_ID, c.Customer_Name, c.Address, c.Postal_Code, c.Phone, c.Division_ID, " +
                    "c.Student_ID, c.Instructor_ID, f.Division, f.Country_ID, co.Country FROM customers as c " +
                    "INNER JOIN first_level_divisions as f on c.Division_ID = f.Division_ID INNER JOIN countries " +
                    "as co ON f.Country_ID = co.Country_ID";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Get data to use in constructor
            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int studentId = rs.getInt("Student_ID");
                int instructorId = rs.getInt("Instructor_ID");
                String division = rs.getString("Division");
                String country = rs.getString("Country");

                // Add new Customer object with data from query
                Customer c = new Customer(customerId, customerName, address, postalCode, phone, studentId,
                        instructorId, division, country);
                allCustomers.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allCustomers;
    }

    /**
     * Inserts a student into the DB.
     *
     * @param customerName
     * @param address
     * @param postalCode
     * @param phone
     * @param country
     * @param divisionId
     * @throws SQLException
     */
    public static void addStudent(String customerName, String address, String postalCode, String phone,
                                  String country, int divisionId, int studentId) throws SQLException {

        // SQL query, format time for input to match DB, and execute
        try {
            String sql = "INSERT INTO customers VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setString(5, ZonedDateTime.now(ZoneOffset.UTC).format(timeFormat));
            ps.setString(6, UserDB.getCurrentUser().getUsername());
            ps.setString(7, ZonedDateTime.now(ZoneOffset.UTC).format(timeFormat));
            ps.setString(8, UserDB.getCurrentUser().getUsername());
            ps.setInt(9, divisionId);
            ps.setInt(10, studentId);
            ps.setNString(11, null);

            ps.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Inserts an instructor into the DB.
     *
     * @param customerName
     * @param address
     * @param postalCode
     * @param phone
     * @param country
     * @param divisionId
     * @param instructorId
     * @throws SQLException
     */
    public static void addInstructor(String customerName, String address, String postalCode, String phone,
                                     String country, int divisionId, int instructorId) throws SQLException {

        // SQL query, format time for input to match DB, and execute
        try {
            String sql = "INSERT INTO customers VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setString(5, ZonedDateTime.now(ZoneOffset.UTC).format(timeFormat));
            ps.setString(6, UserDB.getCurrentUser().getUsername());
            ps.setString(7, ZonedDateTime.now(ZoneOffset.UTC).format(timeFormat));
            ps.setString(8, UserDB.getCurrentUser().getUsername());
            ps.setInt(9, divisionId);
            ps.setNString(10, null);
            ps.setInt(11, instructorId);

            ps.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Updates a student in the DB.
     *
     * @param customerName
     * @param address
     * @param postalCode
     * @param phone
     * @param division
     * @param customerId
     * @throws SQLException
     */
    public static void updateStudent(String customerName, String address, String postalCode, String phone,
                                      String division, String studentInstructorProgram, int customerId) throws SQLException {

        // SQL query, format time for input to match DB, and execute
        try {
            String sql = "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Last_Update=?," +
                    " Last_Updated_By=?, Division_ID=?, Student_ID=? WHERE Customer_ID=?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setString(5, ZonedDateTime.now(ZoneOffset.UTC).format(timeFormat));
            ps.setString(6, UserDB.getCurrentUser().getUsername());
            ps.setInt(7, DivisionDB.getDivisionId(division));
            ps.setInt(8, getStudentId(studentInstructorProgram));
            ps.setInt(9, customerId);

            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Updates a student in the DB.
     *
     * @param customerName
     * @param address
     * @param postalCode
     * @param phone
     * @param division
     * @param customerId
     * @throws SQLException
     */
    public static void updateInstructor(String customerName, String address, String postalCode, String phone,
                                     String division, String studentInstructorProgram, int customerId) throws SQLException {

        // SQL query, format time for input to match DB, and execute
        try {
            String sql = "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Last_Update=?," +
                    " Last_Updated_By=?, Division_ID=?, Instructor_ID=? WHERE Customer_ID=?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setString(5, ZonedDateTime.now(ZoneOffset.UTC).format(timeFormat));
            ps.setString(6, UserDB.getCurrentUser().getUsername());
            ps.setInt(7, DivisionDB.getDivisionId(division));
            ps.setInt(8, getInstructorId(studentInstructorProgram));
            ps.setInt(9, customerId);

            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Deletes a customer from the DB.
     *
     * @param customerId
     * @return
     * @throws SQLException
     */
    public static Boolean deleteCustomer(int customerId) throws SQLException {

        try {
            String sql = "DELETE FROM customers WHERE Customer_ID=?";
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

    public static ObservableList<String> getAllStudents() throws SQLException {

        ObservableList<String> allStudents = FXCollections.observableArrayList();

        // SQL query and execute to a result set
        String sql = "SELECT DISTINCT Program FROM students";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        // Add result set to the list
        while (rs.next()) {
            allStudents.add(rs.getString("Program"));
        }
        return allStudents;
    }

    /**
     * Query to return the student ID associated with the program.
     * @param studentInstructorId
     * @return studentId
     * @throws SQLException
     */
    public static int getStudentId(String studentInstructorId) throws SQLException {

        int studentId = 0;

        // SQL query and execute to a result set
        String sql = "SELECT Program, Student_ID FROM students WHERE Program = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, studentInstructorId);
        ResultSet rs = ps.executeQuery();

        // Add to the variable and return value
        while (rs.next()) {
            studentId = rs.getInt("Student_ID");
        }
        return studentId;
    }

    public static String getStudentProgram(int studentId) throws SQLException {

        String studentProgram = null;

        // SQL query and execute to a result set
        String sql = "SELECT Program, Student_ID FROM students WHERE Student_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, studentId);
        ResultSet rs = ps.executeQuery();

        // Add to the variable and return value
        while (rs.next()) {
            studentProgram = rs.getString("Program");
        }
        return studentProgram;
    }

    public static ObservableList<String> getAllInstructors() throws SQLException {

        ObservableList<String> allInstructors = FXCollections.observableArrayList();

        // SQL query and execute to a result set
        String sql = "SELECT DISTINCT Program FROM instructors";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        // Add result set to the list
        while (rs.next()) {
            allInstructors.add(rs.getString("Program"));
        }
        return allInstructors;
    }

    /**
     * Query to return the student ID associated with the program.
     * @param studentInstructorId
     * @return studentId
     * @throws SQLException
     */
    public static int getInstructorId(String studentInstructorId) throws SQLException {

        int instructorId = 0;

        // SQL query and execute to a result set
        String sql = "SELECT Program, Instructor_ID FROM instructors WHERE Program = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, studentInstructorId);
        ResultSet rs = ps.executeQuery();

        // Add to the variable and return value
        while (rs.next()) {
            instructorId = rs.getInt("Instructor_ID");
        }
        return instructorId;
    }

    public static String getInstructorProgram(int instructorId) throws SQLException {

        String instructorProgram = null;

        // SQL query and execute to a result set
        String sql = "SELECT Program, Instructor_ID FROM instructors WHERE Instructor_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, instructorId);
        ResultSet rs = ps.executeQuery();

        // Add to the variable and return value
        while (rs.next()) {
            instructorProgram = rs.getString("Program");
        }
        return instructorProgram;
    }
}