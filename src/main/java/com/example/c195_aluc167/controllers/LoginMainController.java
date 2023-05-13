package com.example.c195_aluc167.controllers;

import com.example.c195_aluc167.MainApplication;
import connector.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginMainController implements Initializable {

    @FXML private Button lm_login_btn;
    @FXML private Label lm_username_lbl;
    @FXML private Label lm_password_lbl;
    @FXML private TextField lm_username_tf;
    @FXML private TextField lm_password_tf;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Login page has been initialized!");
    }

    public void lm_login_btn_clicked(ActionEvent actionEvent) throws IOException, SQLException {
        if (lm_username_tf.getText().isEmpty() || lm_password_tf.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty fields");
            alert.setContentText("Please ensure that all text fields are \nfilled with the required information.");
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
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided username is incorrect!");
                alert.showAndWait();
            }
            else
            {
                while(resultSet.next())
                {
                    String grabPassword = resultSet.getString("Password");
                    if(grabPassword.equals(lm_password_tf.getText()))
                    {
                        MainApplication.loadScene("sample.fxml", 927, 366, "", actionEvent);
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
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Password is incorrect!");
                        alert.showAndWait();
                    }
                }

            }











        }
    }
}