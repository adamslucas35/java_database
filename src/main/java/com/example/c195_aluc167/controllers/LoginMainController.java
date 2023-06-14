package com.example.c195_aluc167.controllers;

import com.example.c195_aluc167.MainApplication;
import connector.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class LoginMainController implements Initializable {

    @FXML private Button lm_login_btn;
    @FXML private Label lm_username_lbl;
    @FXML private Label lm_password_lbl;
    @FXML private Label lm_location;
    @FXML private TextField lm_username_tf;
    @FXML private PasswordField lm_password_pf;

    /**
     * Initializes loginMain.fxml
     * @param url url
     * @param resourceBundle resourceBundles
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ResourceBundle rb = ResourceBundle.getBundle("Lang", Locale.getDefault());

        // creating location variable to display when page is initialized
        Locale locale = Locale.getDefault();
        String country = locale.getDisplayCountry(locale);
        ResourceBundle bundle = ResourceBundle.getBundle("Lang");
        String locationText = bundle.getString("lm_location");
        String formattedLocation = String.format(locationText, country);
        lm_location.setText(formattedLocation);



            System.out.println(rb.getString("language"));


        LocalDate easternTime = LocalDate.of(2023, 6, 12);
        LocalTime easternDate = LocalTime.of(22, 39);

        String endTime = "23:00";
        String endDate = "2023-06-15";

        String appointmentEnd = endDate + " " + endTime;

        LocalDateTime localAppointmentEnd = LocalDateTime.parse(appointmentEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dateTime = localAppointmentEnd.format(dateTimeFormatter);
        System.out.println(dateTime + " LOCAL?");

       String east =  JDBC.convertLocaltoEastern(localAppointmentEnd).format(dateTimeFormatter);
        System.out.println(east + " EAST!");



        PreparedStatement statement = null;
        try {
            statement = JDBC.connection.prepareStatement("SELECT Start, End from Appointments");
            ResultSet rs = statement.executeQuery();
            while(rs.next())
            {
                System.out.print(rs.getString("Start") + " | " + rs.getString("End") + "\n");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }














//        LocalDate eastDate = LocalDate.now(ZoneId.of("US/Eastern"));
//        LocalTime eastTime = LocalTime.parse(LocalTime.now(ZoneId.of("US/Eastern")).format(DateTimeFormatter.ofPattern("HH:mm")));
//        System.out.println(eastDate + " EST");
//        System.out.println(eastTime + " EST");
//        ZoneId parisZoneId = ZoneId.of("Europe/Paris");
//        ZonedDateTime parisZDT = ZonedDateTime.of(easternTime, easternDate, parisZoneId);
//        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
//
//        Instant paristToGMTInstant = parisZDT.toInstant();
//        ZonedDateTime parisToLocalZDT = parisZDT.withZoneSameInstant(localZoneId);
//        ZonedDateTime gmtToLocalZDT = paristToGMTInstant.atZone(localZoneId);
//
//        System.out.println("LOCAL: " + ZonedDateTime.now());
//        System.out.println("PARIS: " + parisZDT);
//        System.out.println("PARIS->UTC: " + paristToGMTInstant);
//        System.out.println("GMT->LOCAL: " + gmtToLocalZDT);
//
//        System.out.println("GMT->LOCALDATE: " + gmtToLocalZDT.toLocalDate());
//        System.out.println("GMT->LOCALTIME: " + gmtToLocalZDT.toLocalTime());
//
//        String date = String.valueOf(gmtToLocalZDT.toLocalDate());
//        String time = String.valueOf(gmtToLocalZDT.toLocalTime());
//        String dateTime = date + " " + time;
//        System.out.println(dateTime);

    }

    public void lm_login_btn_clicked(ActionEvent actionEvent) throws IOException, SQLException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Lang", Locale.getDefault());
        if (lm_username_tf.getText().isEmpty() || lm_password_pf.getText().isEmpty())
        {
            String errorFields = resourceBundle.getString("errorFields");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(errorFields);
            alert.showAndWait();
        }
        else if (!(lm_username_tf.getText().isEmpty() && lm_password_pf.getText().isEmpty()))
        {

            PreparedStatement checkCredentials = null;
            ResultSet resultSet = null;
            checkCredentials = JDBC.connection.prepareStatement("SELECT Password FROM users WHERE User_Name = ?");
            checkCredentials.setString(1, lm_username_tf.getText());
            resultSet = checkCredentials.executeQuery();

            if (!resultSet.isBeforeFirst())
            {
                String errorUsername = resourceBundle.getString("errorUsername");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(errorUsername);
                alert.showAndWait();
            }
            else
            {
                while(resultSet.next())
                {
                    String grabPassword = resultSet.getString("Password");
                    if(grabPassword.equals(lm_password_pf.getText()))
                    {
                        MainApplication.loadScene("sample.fxml", 370, 261, "", actionEvent);
                        // Load sample page
//                        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("sample.fxml"));
//                        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
//                        Scene scene = new Scene(fxmlLoader.load(), 927, 366);
//                        stage.setScene(scene);
//                        stage.setTitle("");
//                        stage.show();
                    }
                    else
                    {
                        String errorPassword = resourceBundle.getString("errorPassword");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText(errorPassword);
                        alert.showAndWait();
                    }
                }

            }











        }
    }
}