package com.example.c195_aluc167;

import connector.JDBC;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.TimeZone;


public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        ResourceBundle rb = ResourceBundle.getBundle("Lang", Locale.getDefault());
        String welcome = rb.getString("welcome");
        String login = rb.getString("lm_loginTitle");


        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("loginMain.fxml"), rb);

        Scene scene = new Scene(fxmlLoader.load(), 440, 440);
        stage.setX(100);
        stage.setY(100);
        stage.setTitle(welcome + "-" + login);
        stage.setScene(scene);
        stage.show();


    }

    public static void return_to_load(ActionEvent actionEvent) throws IOException {
        loadScene("sample.fxml", 370, 261, "", actionEvent);
    }

    public static void loadScene(String fxmlFile, double width, double height, String title, ActionEvent actionEvent) throws IOException {
        ResourceBundle rb = ResourceBundle.getBundle("Lang", Locale.getDefault());

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxmlFile), rb);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, width, height);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setX(100);
        stage.setY(100);
        stage.show();
    }




    public static void main(String[] args) {

        JDBC.openConnection();
        launch();
        JDBC.closeConnection();
    }
}