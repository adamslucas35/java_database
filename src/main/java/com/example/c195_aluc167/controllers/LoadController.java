package com.example.c195_aluc167.controllers;

import com.example.c195_aluc167.MainApplication;
import connector.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Window to load appointments, customers and reports page.
 */
public class LoadController implements Initializable {

    public void logOut_clicked(ActionEvent actionEvent) throws IOException
    {
        MainApplication.loadScene("loginMain.fxml", 440, 440, "", actionEvent);
//        ResourceBundle rb = ResourceBundle.getBundle("Lang", Locale.getDefault());
//
//        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("loginMain.fxml"), rb);
//        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
//        Scene scene = new Scene(fxmlLoader.load(), 440, 440);
//        stage.setScene(scene);
//        stage.setTitle("Welcome - Login");
//        stage.show();
    }

    /**
     * initializes load.fxml
     * @param url url
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        System.out.println("Load page has been initialized!");
    }

    /**
     * Opens customers.fxml
     * @param actionEvent when button is clicked.
     * @throws IOException throws Input Output error
     */
    public void ld_customer_clicked(ActionEvent actionEvent) throws IOException {
        MainApplication.loadScene("customers.fxml", 1050, 500, "", actionEvent);
    }

    /**
     * Opens appointments.fxml
     * @param actionEvent when button is clicked.
     * @throws IOException throws Input Output error
     */
    public void ld_appointment_clicked(ActionEvent actionEvent) throws IOException {
        ResourceBundle rb = ResourceBundle.getBundle("Lang", Locale.getDefault());
        String rightNow = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(JDBC.convertLocaltoUTC(LocalDateTime.now()));
        String fifteenFromNow = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(JDBC.convertLocaltoUTC(LocalDateTime.now().plusMinutes(15)));

        try {
            PreparedStatement appSoon = JDBC.connection.prepareStatement("SELECT * FROM appointments WHERE Start BETWEEN ? AND ?");
            appSoon.setString(1, rightNow);
            appSoon.setString(2, fifteenFromNow);
            ResultSet result = appSoon.executeQuery();

            Alert popup = new Alert(Alert.AlertType.WARNING);
            popup.setX(150);
            popup.setY(150);

            if(result.next()) {
                String appointmentID = result.getString("Appointment_ID");
                LocalDateTime appointmentDateTimeUTC = result.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appointmentDateTimeLocal = JDBC.convertUTCtoLocal(appointmentDateTimeUTC);
                String haveApp = rb.getString("haveAppointment");
                String output = String.format(haveApp, appointmentID, appointmentDateTimeLocal.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                popup.setContentText(output);
                popup.showAndWait();
            }
            else {
                String noHaveApp = rb.getString("noHaveAppointment");
                popup.setContentText(noHaveApp);
                popup.showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        MainApplication.loadScene("appointments.fxml", 1200, 500, "", actionEvent);

    }

    /**
     * Goes back to loginMain.fxml
     * @param actionEvent when button is clicked.
     * @throws IOException throws Input Output error
     */
    public void log_out(ActionEvent actionEvent) throws IOException {
        MainApplication.loadScene("loginMain.fxml", 440, 440, "", actionEvent);
    }

    /**
     * Opens reports.fxml
     * @param actionEvent when button is clicked.
     * @throws IOException throws Input Output error
     */
    public void clickedReports(ActionEvent actionEvent) throws IOException {
        MainApplication.loadScene("reports.fxml", 850, 560, "", actionEvent);
    }
}
