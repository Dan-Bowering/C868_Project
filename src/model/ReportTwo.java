package model;

import java.sql.Timestamp;

public class ReportTwo {

    private int appointmentId;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private int contactId;


    public ReportTwo(int appointmentId, String type, Timestamp start, Timestamp end, int contactId) {
        this.appointmentId = appointmentId;
        this.type = type;
        this.start = start;
        this.end = end;
        this.contactId = contactId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

}
