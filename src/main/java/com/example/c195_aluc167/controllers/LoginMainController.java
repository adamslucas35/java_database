package com.example.c195_aluc167.controllers;

import com.example.c195_aluc167.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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

    public void lm_login_btn_clicked(ActionEvent actionEvent) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("sample.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 927, 366);
        stage.setScene(scene);
        stage.setTitle("");
        stage.show();
    }
}