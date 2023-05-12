package com.example.c195_aluc167.controllers;

import com.example.c195_aluc167.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class SampleController {

    public void goBack_clicked(ActionEvent actionEvent) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("loginMain.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 440, 440);
        stage.setScene(scene);
        stage.setTitle("Welcome - Login");
        stage.show();
    }
}
