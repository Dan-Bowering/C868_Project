package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    Customer customer = new Customer(10, "Dan Bowering", "123 Street", "12345",
            "123-456-7890", 2000, 0, "Michigan", "U.S");

    @Test
    void getCustomerId() {
        int expected = 10;
        int actual = customer.getCustomerId();
        assertEquals(expected, actual);
    }

    @Test
    void getCustomerName() {
        String expected = "Dan Bowering";
        String actual = customer.getCustomerName();
        assertEquals(expected, actual);
    }

    @Test
    void getAddress() {
        String expected = "123 Street";
        String actual = customer.getAddress();
        assertEquals(expected, actual);
    }

    @Test
    void getPostalCode() {
        String expected = "12345";
        String actual = customer.getPostalCode();
        assertEquals(expected, actual);
    }

    @Test
    void getPhone() {
        String expected = "123-456-7890";
        String actual = customer.getPhone();
        assertEquals(expected, actual);
    }

    @Test
    void getDivision() {
        String expected = "Michigan";
        String actual = customer.getDivision();
        assertEquals(expected, actual);
    }

    @Test
    void getCountry() {
        String expected = "U.S";
        String actual = customer.getCountry();
        assertEquals(expected, actual);
    }

    @Test
    void getStudentId() {
        int expected = 2000;
        int actual = customer.getStudentId();
        assertEquals(expected, actual);
    }

    @Test
    void getInstructorId() {
        int expected = 0;
        int actual = customer.getInstructorId();
        assertEquals(expected, actual);
    }
}