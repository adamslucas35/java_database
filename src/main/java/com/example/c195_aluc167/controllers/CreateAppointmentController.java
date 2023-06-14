package com.example.c195_aluc167.controllers;

import com.example.c195_aluc167.MainApplication;
import connector.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import static connector.JDBC.connection;

public class CreateAppointmentController implements Initializable {

    public TextField ma_appointmentID_tf;
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

    public void appointmentContactClicked(MouseEvent mouseEvent) throws SQLException
    {
        PreparedStatement getData = JDBC.connection.prepareStatement("SELECT Contact_Name FROM contacts");
        ResultSet rs = getData.executeQuery();
        ap_appointmentContact_cb.getItems().clear();
        while (rs.next())
        {
            ap_appointmentContact_cb.getItems().add(rs.getString("Contact_Name"));
        }
    }

    public void onSubmit(ActionEvent actionEvent) throws SQLException, IOException
    {

        String appointmentTitle = ap_appointmentTitle_tf.getText();
        String appointmentDescription = ap_appointmentDescription_tf.getText();
        String appointmentLocation = ap_appointmentLocation_tf.getText();
        String contactID = null;

        PreparedStatement contactCheck = connection.prepareStatement("SELECT Contact_ID FROM contacts WHERE Contact_Name = ?");
        contactCheck.setString(1, (String) ap_appointmentContact_cb.getValue());
        ResultSet resultSet = contactCheck.executeQuery();
        while (resultSet.next()) {
            contactID = resultSet.getString("Contact_ID");
        }

        String appointmentType = ap_appointmentType_tf.getText();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String appointmentStartDate = "";
        String appointmentEndDate = "";

//AppointmentStart
        LocalDate startDate = ap_startDate.getValue();
        if (startDate != null) {
            appointmentStartDate = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            String startDateText = ap_startDate.getEditor().getText();
            if(!startDateText.isEmpty()){
                try {
                    appointmentStartDate = LocalDate.parse(startDateText).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                } catch (Exception e)
                {
                    String formatError = rb.getString("formatErrorDate");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(formatError);
                    alert.showAndWait();
                }
            }
        }

        String appointmentStartTime = ap_appointmentStart_tf.getText();
        boolean startHasLeadingZero = appointmentStartTime.charAt(0) == '0';
        int startFirstChar = Integer.parseInt(String.valueOf(appointmentStartTime.charAt(0)));
        int startSecondChar = appointmentStartTime.charAt(1);
        if (startHasLeadingZero || (startFirstChar > 0 && startSecondChar != 58)) {
            try {
                LocalTime.parse(appointmentStartTime, DateTimeFormatter.ofPattern("HH:mm"));
            } catch (DateTimeParseException e)
            {
                String formatError = rb.getString("formatErrorTime");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(formatError);
                alert.showAndWait();
            }
        }
        else
        {
            try {
                appointmentStartTime = "0" + appointmentStartTime;
                LocalTime.parse(appointmentStartTime, DateTimeFormatter.ofPattern("HH:mm"));
            } catch (DateTimeParseException e)
            {
                String formatError = rb.getString("formatErrorTime");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(formatError);
                alert.showAndWait();
            }
        }
        String appointmentStart = appointmentStartDate + " " + appointmentStartTime;
        LocalDateTime localAppointmentStart = LocalDateTime.parse(appointmentStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        appointmentStart = String.valueOf(JDBC.convertLocaltoUTC(localAppointmentStart));

        //AppointmentEnd
        LocalDate endDate = ap_endDate.getValue();
        if (endDate != null) {
            appointmentEndDate = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            String endDateText = ap_endDate.getEditor().getText();
            if (!endDateText.isEmpty()) {
                try {
                    appointmentEndDate = LocalDate.parse(endDateText).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                } catch (Exception e) {
                    String formatError = rb.getString("formatErrorTime");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(formatError);
                    alert.showAndWait();
                }
            }
        }
        String appointmentEndTime = ap_appointmentEnd_tf.getText();
        boolean endHasLeadingZero = appointmentEndTime.charAt(0) == '0';
        int endFirstChar = Integer.parseInt(String.valueOf(appointmentEndTime.charAt(0)));
        int endSecondChar = appointmentEndTime.charAt(1);
        if (endHasLeadingZero || (endFirstChar > 0 && endSecondChar != 58)) {
            try {
                LocalTime.parse(appointmentEndTime, DateTimeFormatter.ofPattern("HH:mm"));
            } catch (DateTimeParseException e) {
                String formatError = rb.getString("formatErrorTime");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(formatError);
                alert.showAndWait();
            }
        } else {
            try {
                appointmentEndTime = "0" + appointmentEndTime;
                LocalTime.parse(appointmentEndTime, DateTimeFormatter.ofPattern("HH:mm"));
            } catch (DateTimeParseException e) {
                String formatError = rb.getString("formatErrorTime");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(formatError);
                alert.showAndWait();
            }
        }
        String appointmentEnd = appointmentEndDate + " " + appointmentEndTime;
        LocalDateTime localAppointmentEnd = LocalDateTime.parse(appointmentEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        appointmentEnd = String.valueOf(JDBC.convertLocaltoUTC(localAppointmentEnd));


        String appointmentCustomerID = ap_appointmentCustomerID_tf.getText();
        String appointmentUserID = ap_appointmentUserID_tf.getText();


        //Business Hours Check
        if (checkBusinessHours(localAppointmentStart, localAppointmentEnd, rb)) return;

        //Appointment Overlap
        if (checkAppOverlap(appointmentStart, appointmentEnd, rb)) return;


        //Execute Update
        PreparedStatement createAppointment = connection.prepareStatement("INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        createAppointment.setString(1, appointmentTitle);
        createAppointment.setString(2, appointmentDescription);
        createAppointment.setString(3, appointmentLocation);
        createAppointment.setString(4, appointmentType);
        createAppointment.setString(5, appointmentStart);
        createAppointment.setString(6, appointmentEnd);
        createAppointment.setString(7, appointmentCustomerID);
        createAppointment.setString(8, appointmentUserID);
        createAppointment.setString(9, contactID);
        createAppointment.executeUpdate();


        MainApplication.loadScene("appointments.fxml", 1200, 500, "", actionEvent);

    }

    static boolean checkAppOverlap(String appointmentStart, String appointmentEnd, ResourceBundle rb) throws SQLException {
        PreparedStatement overlap = JDBC.connection.prepareStatement("SELECT * FROM appointments " +
                "WHERE ((? < End AND ? > Start) OR (? <= Start AND ? >= End))");
        overlap.setString(1, appointmentStart);
        overlap.setString(2, appointmentEnd);
        overlap.setString(3, appointmentStart);
        overlap.setString(4, appointmentEnd);
        ResultSet overlapResult = overlap.executeQuery();
        if(overlapResult.next())
        {
            String exists = rb.getString("appointmentExists");
            Alert existing = new Alert(Alert.AlertType.WARNING);
            existing.setContentText(exists);
            existing.showAndWait();
            return true;
        }
        return false;
    }

    static boolean checkBusinessHours(LocalDateTime localAppointmentStart, LocalDateTime localAppointmentEnd, ResourceBundle rb) {
        ZonedDateTime easternStart = JDBC.convertLocaltoEastern(localAppointmentStart);
        ZonedDateTime easternEnd = JDBC.convertLocaltoEastern(localAppointmentEnd);
        int easternStartTime = easternStart.getHour();
        int easternEndTime = easternEnd.getHour();
        if (!(easternStartTime >= 8 && easternEndTime < 22))
        {
            String closedHours = rb.getString("openedHours");
            Alert closed = new Alert(Alert.AlertType.ERROR);
            closed.setContentText(closedHours);
            closed.showAndWait();
            return true;
        }
        return false;
    }



    public void goBack(ActionEvent actionEvent)
    {
        try {
            MainApplication.loadScene("appointments.fxml", 1200, 500, "", actionEvent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
