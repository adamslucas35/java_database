package com.example.c195_aluc167.controllers;

import com.example.c195_aluc167.MainApplication;
import connector.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Appointments;
import model.Contacts;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Form to modify appointment.
 */
public class ModifyAppointmentController implements Initializable {

    public TextField ma_appointmentID_tf;
    public TextField ma_appointmentTitle_tf;
    public TextField ma_appointmentDescription_tf;
    public TextField ma_appointmentLocation_tf;
    public ComboBox ma_appointmentContact_cb;
    public TextField ma_appointmentType_tf;
    public DatePicker ma_startDate;
    public TextField ma_appointmentStart_tf;
    public DatePicker ma_endDate;
    public TextField ma_appointmentEnd_tf;
    public TextField ma_appointmentCustomerID_tf;
    public TextField ma_appointmentUserID_tf;


    ResourceBundle rb = ResourceBundle.getBundle("Lang", Locale.getDefault());

    /**
     * Initializes modifyApointment.
     * @param url url
     * @param resourceBundle resourebundle Lang
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        ma_appointmentID_tf.setEditable(false);
        ma_appointmentID_tf.setDisable(true);
    }

    /**
     * Receives data from selected appointment in appointments.fxml
     * @param appointments appointments object
     * @param contacts contacts object
     */
    public void receiveAppointment(Appointments appointments, Contacts contacts)
    {
        ma_appointmentID_tf.setText(Integer.toString(appointments.getAppointmentId()));
        ma_appointmentTitle_tf.setText(appointments.getTitle());
        ma_appointmentDescription_tf.setText(appointments.getDescription());
        ma_appointmentLocation_tf.setText(appointments.getLocation());
        ma_appointmentContact_cb.setValue(contacts.getContactName());
        ma_appointmentType_tf.setText(appointments.getType());
        ma_startDate.getEditor().setText(String.valueOf(appointments.getStartDate()));
        ma_appointmentStart_tf.setText(String.valueOf(appointments.getStartTime()));
        ma_endDate.getEditor().setText(String.valueOf(appointments.getEndDate()));
        ma_appointmentEnd_tf.setText(String.valueOf(appointments.getEndTime()));
        ma_appointmentCustomerID_tf.setText(Integer.toString(appointments.getCustomerId()));
        ma_appointmentUserID_tf.setText(Integer.toString(appointments.getUserId()));

    }

    /**
     * Fills combo box with data for Contact Names
     * @param mouseEvent when box is clicked.
     * @throws SQLException sql query error
     */
    public void appointmentContactClicked(MouseEvent mouseEvent) throws SQLException
    {
        PreparedStatement getData = JDBC.connection.prepareStatement("SELECT Contact_Name FROM contacts");
        ResultSet rs = getData.executeQuery();
        ma_appointmentContact_cb.getItems().clear();
        while (rs.next())
        {
            ma_appointmentContact_cb.getItems().add(rs.getString("Contact_Name"));
        }
    }

    /**
     * Modifies database with all the field's data.
     * @param actionEvent when button is pressed
     * @throws IOException input output error
     * @throws SQLException sql query error
     */
    public void onSubmit(ActionEvent actionEvent) throws IOException, SQLException {
        String appointmentStartDate = "";
        String appointmentEndDate = "";

        LocalDate startDate = ma_startDate.getValue();
        if (startDate != null) {
            appointmentStartDate = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            String startDateText = ma_startDate.getEditor().getText();
            if (!startDateText.isEmpty()) {
                try {
                    appointmentStartDate = LocalDate.parse(startDateText).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                } catch (Exception e) {
                    String formatError = rb.getString("formatErrorDate");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(formatError);
                    alert.showAndWait();
                    return;
                }

            }
        }

        String appointmentStartTime = ma_appointmentStart_tf.getText();
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
                return;
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
                return;
            }
        }

        /** This converts the entered string from the local time to UTC time to be stored correctly in the database. */
        String appointmentStart = appointmentStartDate + " " + appointmentStartTime;
        LocalDateTime localAppointmentStart = LocalDateTime.parse(appointmentStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        appointmentStart = String.valueOf(JDBC.convertLocaltoUTC(localAppointmentStart));


        LocalDate endDate = ma_endDate.getValue();
        if (endDate != null) {
            appointmentEndDate = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            String endDateText = ma_endDate.getEditor().getText();
            if (!endDateText.isEmpty()) {
                try {
                    appointmentEndDate = LocalDate.parse(endDateText).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                } catch (Exception e) {
                    String formatError = rb.getString("formatErrorDate");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(formatError);
                    alert.showAndWait();
                    return;
                }
            }
        }


        String appointmentEndTime = ma_appointmentEnd_tf.getText();

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
                return;
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
                return;
            }
        }

        String appointmentEnd = appointmentEndDate + " " + appointmentEndTime;
        LocalDateTime localAppointmentEnd = LocalDateTime.parse(appointmentEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        appointmentEnd = String.valueOf(JDBC.convertLocaltoUTC(localAppointmentEnd));

        //Reversed hours
        if(localAppointmentEnd.isBefore(localAppointmentStart))
        {
            String apOverlap = rb.getString("appointmentOverlap");
            Alert overlap = new Alert(Alert.AlertType.WARNING);
            overlap.setContentText(apOverlap);
            overlap.showAndWait();
            return;
        }

        //Business Hours Check
        if (CreateAppointmentController.checkBusinessHours(localAppointmentStart, localAppointmentEnd, rb)) return;

        //Appointment Overlap
        if (checkAppOverlap(appointmentStart, appointmentEnd, ma_appointmentID_tf, rb))
            return;


        PreparedStatement modify = JDBC.connection.prepareStatement("UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?");
        modify.setString(1, ma_appointmentTitle_tf.getText());
        modify.setString(2, ma_appointmentDescription_tf.getText());
        modify.setString(3, ma_appointmentLocation_tf.getText());
        modify.setString(4, ma_appointmentType_tf.getText());
        modify.setString(5, appointmentStart);
        modify.setString(6, appointmentEnd);
        modify.setString(7, ma_appointmentCustomerID_tf.getText());
        modify.setString(8, ma_appointmentUserID_tf.getText());

        PreparedStatement contact = JDBC.connection.prepareStatement("SELECT c.Contact_ID from Contacts c WHERE Contact_Name = ?");
        contact.setString(1, (String)ma_appointmentContact_cb.getValue());
        ResultSet rs = contact.executeQuery();
        while (rs.next())
        {
            modify.setString(9, rs.getString("Contact_ID"));
        }

        modify.setString(10, ma_appointmentID_tf.getText());
        modify.executeUpdate();

        MainApplication.loadScene("appointments.fxml", 1200, 500, "", actionEvent);
    }

    /**
     * Returns to appointments.fxml.
     * @param actionEvent when button is pressed.
     * @throws IOException input output error
     */
    public void goBack(ActionEvent actionEvent) throws IOException
    {
        MainApplication.loadScene("appointments.fxml", 1200, 500, "", actionEvent);

    }

    /**
     * Check to see if appointment starts before it ends.
     * @param appointmentStart appointmentStart dateTime
     * @param appointmentEnd appointmentEnd dateTiem
     * @param rb ResourceBundle Lang
     * @return to end function
     * @throws SQLException throws error if sql query is not correct
     */
    static boolean checkAppOverlap(String appointmentStart, String appointmentEnd, TextField maAppointmentIDTf, ResourceBundle rb) throws SQLException {
        PreparedStatement overlap = JDBC.connection.prepareStatement("SELECT * FROM appointments " +
                "WHERE ((? < End AND ? > Start) OR (? <= Start AND ? >= End) OR (? >= Start AND ? <= End)) " +
                "AND Appointment_ID != ?");
        overlap.setString(1, appointmentStart);
        overlap.setString(2, appointmentEnd);
        overlap.setString(3, appointmentStart);
        overlap.setString(4, appointmentEnd);
        overlap.setString(5, appointmentStart);
        overlap.setString(6, appointmentEnd);
        overlap.setString(7, maAppointmentIDTf.getText());
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


}

