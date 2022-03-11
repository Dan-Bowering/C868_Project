package model;

import utility.CustomerDB;

import java.sql.SQLException;

public class Student extends Customer{

    private static int studentId;
    private static String studentProgram;

    public Student(int customerId, String customerName, String address, String postalCode, String phone, int studentId,
                   int instructorId, String studentProgram,String division, String country) {
        super(customerId, customerName, address, postalCode, phone, studentId, instructorId, division, country);
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public static String getStudentProgram() {
        return studentProgram;
    }

    public static void setStudentProgram(String studentProgram) {
        Student.studentProgram = studentProgram;
    }
}
