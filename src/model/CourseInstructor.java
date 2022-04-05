package model;

import utility.CustomerDB;

import java.sql.SQLException;

public class CourseInstructor extends Customer{

    private static int instructorId;
    private static String instructorProgram;

    public CourseInstructor(int customerId, String customerName, String address, String postalCode, String phone,
                            int studentId, int instructorId, String instructorProgram, String division, String country) {
        super(customerId, customerName, address, postalCode, phone, studentId, instructorId, division, country);
    }

    public int getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }

    public static String getInstructorProgram() {
        return instructorProgram;
    }

    public static void setInstructorProgram(String instructorProgram) {
        CourseInstructor.instructorProgram = instructorProgram;
    }
}
