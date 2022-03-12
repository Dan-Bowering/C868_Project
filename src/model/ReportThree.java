package model;

public class ReportThree {

    private int studentId;
    private String customerName;
    private String phoneNumber;

    public ReportThree(int studentId, String customerName, String phoneNumber) {
        this.studentId = studentId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
