package com.example.c195_aluc167.controllers;

import com.example.c195_aluc167.MainApplication;
import connector.JDBC;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.PropertyPermission;
import java.util.ResourceBundle;

import static connector.JDBC.connection;

public class CreateAppointmentController implements Initializable {

    @FXML private TextField ap_appointmentID_tf;
    @FXML private TextField ap_appointmentTitle_tf;
    @FXML private TextField ap_appointmentDescription_tf;
    @FXML private TextField ap_appointmentLocation_tf;
    @FXML private ComboBox ap_appointmentContact_cb;
    @FXML private TextField ap_appointmentType_tf;
    @FXML private TextField ap_appointmentStart_tf;
    @FXML private TextField ap_appointmentEnd_tf;
    @FXML private TextField ap_appointmentCustomerID_tf;
    @FXML private TextField ap_appointmentUserID_tf;
    @FXML private DatePicker ap_startDate;
    @FXML private DatePicker ap_endDate;
    ResourceBundle rb = ResourceBundle.getBundle("Lang", Locale.getDefault());
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        String auto = rb.getString("autoGen");
        ap_appointmentID_tf.setDisable(true);
        ap_appointmentID_tf.setEditable(false);
        ap_appointmentID_tf.setText(auto);
    }

    public void appointmentContactClicked(MouseEvent mouseEvent) throws SQLException {
        PreparedStatement getData = JDBC.connection.prepareStatement("SELECT Contact_Name FROM contacts");
        ResultSet rs = getData.executeQuery();
        ap_appointmentContact_cb.getItems().clear();
        while (rs.next())
        {
            ap_appointmentContact_cb.getItems().add(rs.getString("Contact_Name"));
        }
    }

    public void onSubmit(ActionEvent actionEvent) throws SQLException, IOException {

        String appointmentTitle = ap_appointmentTitle_tf.getText();
        String appointmentDescription = ap_appointmentDescription_tf.getText();
        String appointmentLocation = ap_appointmentLocation_tf.getText();
        String contactID = null;
        PreparedStatement contactCheck = connection.prepareStatement("SELECT Contact_ID FROM contacts WHERE Contact_Name = ?");
        contactCheck.setString(1, (String) ap_appointmentContact_cb.getValue());
        ResultSet resultSet = contactCheck.executeQuery();
        while(resultSet.next())
        {
            contactID = resultSet.getString("Contact_ID");
        }
        String appointmentType = ap_appointmentType_tf.getText();
        String appointmentStartDate = ap_startDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String appointmentStartTime = ap_appointmentStart_tf.getText();
        String appointmentStart = appointmentStartDate + " " + appointmentStartTime;
        String appointmentEndDate = ap_endDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String appointmentEndTime = ap_appointmentEnd_tf.getText();
        String appointmentEnd = appointmentEndDate + " " + appointmentEndTime;
        String appointmentCustomerID = ap_appointmentCustomerID_tf.getText();
        String appointmentUserID = ap_appointmentUserID_tf.getText();

        PreparedStatement createAppointment = connection.prepareStatement("INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        createAppointment.setString(1, appointmentTitle);
        createAppointment.setString(2, appointmentDescription);
        createAppointment.setString(3, appointmentLocation);
        createAppointment.setString(4, appointmentType);
        createAppointment.setString(5, appointmentStart);
        createAppointment.setString(6, appointmentEnd);
        createAppointment.setString(7, appointmentCustomerID);
        createAppointment.setString(8 ,appointmentUserID);
        createAppointment.setString(9, contactID);
        createAppointment.executeUpdate();

        MainApplication.loadScene("appointments.fxml", 1550, 500, "", actionEvent);


    }

    public void goBack(ActionEvent actionEvent)
    {
        try {
            MainApplication.loadScene("appointments.fxml", 1550, 500, "", actionEvent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
