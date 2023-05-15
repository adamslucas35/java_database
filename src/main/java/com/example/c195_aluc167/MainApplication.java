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
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ResourceBundle rb = ResourceBundle.getBundle("Lang", Locale.getDefault());

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("loginMain.fxml"), rb);

        Scene scene = new Scene(fxmlLoader.load(), 440, 440);
        stage.setTitle("Welcome - Login!");
        stage.setScene(scene);
        stage.show();
    }

    public static void loadScene(String fxmlFile, double width, double height, String title, ActionEvent actionEvent) throws IOException {
        ResourceBundle rb = ResourceBundle.getBundle("Lang", Locale.getDefault());

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxmlFile), rb);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, width, height);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
        if((Locale.getDefault().getLanguage().equals("de")) || (Locale.getDefault().getLanguage().equals("es")) || (Locale.getDefault().getLanguage().equals("fr")))
        {
            System.out.println(rb.getString("hello") + " " + rb.getString("world"));
        }
    }

    public static void main(String[] args) {


        ResourceBundle rb = ResourceBundle.getBundle("Lang", Locale.getDefault());

        if((Locale.getDefault().getLanguage().equals("de")) || (Locale.getDefault().getLanguage().equals("es")) || (Locale.getDefault().getLanguage().equals("fr")))
        {
            System.out.println(rb.getString("hello") + " " + rb.getString("world"));
        }

        JDBC.openConnection();
        launch();
        JDBC.closeConnection();
    }
}