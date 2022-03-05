package model;

public class Student extends Customer{

    private int studentId;
    private String name;

    public Student(int customerId, String customerName, String address, String postalCode, String phone,
                   String division, String country, int studentId, String name) {
        super(customerId, customerName, address, postalCode, phone, division, country);
        this.studentId = studentId;
        this.name = name;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
