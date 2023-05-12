package com.example.c195_aluc167;

import connector.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("loginMain.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 440, 440);
        stage.setTitle("Welcome - Login!");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        JDBC.openConnection();
        launch();
        JDBC.closeConnection();
    }
}