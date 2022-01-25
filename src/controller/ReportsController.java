package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import utility.AppointmentDB;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    @FXML TextArea reportTextArea;

    @FXML
    public void totalAppointmentsByType(ActionEvent event) throws SQLException {

        ObservableList<String> reportResults = AppointmentDB.appointmentsByTypeAndMonth();

        reportTextArea.setText(String.valueOf(reportResults));


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
