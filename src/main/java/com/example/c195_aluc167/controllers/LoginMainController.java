package com.example.c195_aluc167.controllers;

import com.example.c195_aluc167.MainApplication;
import connector.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginMainController implements Initializable {

    @FXML private Button lm_login_btn;
    @FXML private Label lm_username_lbl;
    @FXML private Label lm_password_lbl;
    @FXML private Label lm_location;
    @FXML private TextField lm_username_tf;
    @FXML private TextField lm_password_tf;

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


        //temp to confirm language settings of computer locale
        if((Locale.getDefault().getLanguage().equals("de")) || (Locale.getDefault().getLanguage().equals("es")) || (Locale.getDefault().getLanguage().equals("fr")))
        {
            System.out.println(rb.getString("language"));
        }
    }

    public void lm_login_btn_clicked(ActionEvent actionEvent) throws IOException, SQLException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Lang", Locale.getDefault());
        if (lm_username_tf.getText().isEmpty() || lm_password_tf.getText().isEmpty())
        {
            String errorFields = resourceBundle.getString("errorFields");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(errorFields);
            alert.showAndWait();
        }
        else if (!(lm_username_tf.getText().isEmpty() && lm_password_tf.getText().isEmpty()))
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
                    if(grabPassword.equals(lm_password_tf.getText()))
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