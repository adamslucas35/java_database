package com.example.c195_aluc167.controllers;

import com.example.c195_aluc167.MainApplication;
import connector.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileWriter;
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

/**
 * Login window.
 */
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




    }

    /**
     * When submit button is pushed, function verifies that username and password match and are correct.
     * @param actionEvent when button is clicked.
     * @throws IOException throws error if input or output are bad
     * @throws SQLException throws error if sql query is bad.
     */
    public void lm_login_btn_clicked(ActionEvent actionEvent) throws IOException, SQLException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Lang", Locale.getDefault());
        if (lm_username_tf.getText().isEmpty() || lm_password_pf.getText().isEmpty()) {
            writeToFile(false);
            String errorFields = resourceBundle.getString("errorFields");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(errorFields);
            alert.showAndWait();
        } else if (!(lm_username_tf.getText().isEmpty() && lm_password_pf.getText().isEmpty())) {

            PreparedStatement checkCredentials = null;
            ResultSet resultSet = null;
            checkCredentials = JDBC.connection.prepareStatement("SELECT Password FROM users WHERE User_Name = ?");
            checkCredentials.setString(1, lm_username_tf.getText());
            resultSet = checkCredentials.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                writeToFile(false);
                String errorUsername = resourceBundle.getString("errorUsername");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(errorUsername);
                alert.showAndWait();
            } else {
                while (resultSet.next()) {
                    String grabPassword = resultSet.getString("Password");
                    if (grabPassword.equals(lm_password_pf.getText())) {
                        writeToFile(true);
                        MainApplication.loadScene("load.fxml", 370, 261, "", actionEvent);
                    } else {
                        writeToFile(false);
                        String errorPassword = resourceBundle.getString("errorPassword");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText(errorPassword);
                        alert.showAndWait();
                    }
                }

            }
        }
    }

    /**
     * Function writes to login_activity.txt whether true or false is passed.
     * @param passFail true or false
     * @throws IOException throws exception is input or output error is found.
     */
    public void writeToFile(boolean passFail) throws IOException
    {
        File myFile = new File("login_activity.txt");
        FileWriter writer = new FileWriter(myFile, true);
        if (passFail)
        {
            String successful = "Sucessful" + " | " + "%s" + " | " + "%s\n";
            successful = String.format(successful, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            writer.append(successful);
        }
        else
        {
            String failed = "  Failed " + " | " + "%s" + " | " + "%s\n";
            failed = String.format(failed, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            writer.append(failed);
        }
        writer.close();
    }













}