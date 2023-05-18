package com.example.c195_aluc167.controllers;

import com.example.c195_aluc167.MainApplication;
import connector.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


/**
 * Controls customers.fxml file
 */
public class CustomersController extends MainApplication implements Initializable  {

    @FXML private ObservableList<ObservableList> customerData;

    @FXML private TableView customerTableView;
    private String selectCustomers = "SELECT * FROM customers";

    /**
     * Initialization of customers.fxml
     * @param url url
     * @param resourceBundle looking for resource bundle.
     */
    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerData = FXCollections.observableArrayList();
        try {
            PreparedStatement loadCustomer = JDBC.connection.prepareStatement(selectCustomers);
            ResultSet rs = loadCustomer.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void cs_return_to_load(ActionEvent actionEvent) throws IOException {
        MainApplication.return_to_load(actionEvent);
    }
}
