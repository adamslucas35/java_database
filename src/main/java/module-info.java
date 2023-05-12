module com.example.c196__aluc167 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.c195_aluc167 to javafx.fxml;
    exports com.example.c195_aluc167;
    exports com.example.c195_aluc167.controllers;
    opens com.example.c195_aluc167.controllers to javafx.fxml;
}