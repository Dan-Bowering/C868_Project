package model;

public class CourseInstructor extends Customer{

    private int instructorId;
    private String name;

    public CourseInstructor(int customerId, String customerName, String address, String postalCode,
                            String phone, String division, String country, int instructorId, String name) {
        super(customerId, customerName, address, postalCode, phone, division, country);
        this.instructorId = instructorId;
        this.name = name;
    }

    public int getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
