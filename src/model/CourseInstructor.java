package model;

public class CourseInstructor extends Customer{

    private static int instructorId;

    public CourseInstructor(int customerId, String customerName, String address, String postalCode, String phone,
                            int studentId, int instructorId, String division, String country) {
        super(customerId, customerName, address, postalCode, phone, studentId, instructorId, division, country);
    }

    public int getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }

    public static int getNewInstructorId() {
        return instructorId++;
    }
}
