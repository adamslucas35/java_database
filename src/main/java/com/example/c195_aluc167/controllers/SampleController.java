package com.example.c195_aluc167.controllers;

import com.example.c195_aluc167.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;


public class SampleController implements Initializable {

    public void goBack_clicked(ActionEvent actionEvent) throws IOException
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        System.out.println("Sample page has been initialized!");
    }
}
