package com.example.c195_aluc167.controllers;

import com.example.c195_aluc167.MainApplication;
import connector.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import model.Customers;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ModifyCustomerController implements Initializable {

    @FXML private TextField mc_customerID_tf;
    @FXML private TextField mc_customerName_tf;
    @FXML private TextField mc_customerAddress_tf;
    @FXML private TextField mc_customerPostal_tf;
    @FXML private TextField mc_customerPhone_tf;

    ResourceBundle rb = ResourceBundle.getBundle("Lang", Locale.getDefault());



    public void receiveCustomer(Customers customers)
    {

        mc_customerID_tf.setText(Integer.toString(customers.getCustomerId()));
        mc_customerName_tf.setText(customers.getCustomerName());
        mc_customerAddress_tf.setText(customers.getCustomerAddress());
        mc_customerPostal_tf.setText(customers.getCustomerPostalCode());
        mc_customerPhone_tf.setText(customers.getCustomerPhone());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mc_customerID_tf.setEditable(false);
        mc_customerID_tf.setDisable(true);
    }

    public void onSubmit(ActionEvent actionEvent)
    {
        try {
            PreparedStatement updateQuery = JDBC.connection.prepareStatement("UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ? WHERE Customer_ID = ?");
            updateQuery.setString(1, mc_customerName_tf.getText());
            updateQuery.setString(2, mc_customerAddress_tf.getText());
            updateQuery.setString(3, mc_customerPostal_tf.getText());
            updateQuery.setString(4, mc_customerPhone_tf.getText());
            updateQuery.setString(5, mc_customerID_tf.getText());

            updateQuery.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            MainApplication.loadScene("customers.fxml", 1050, 500, "", actionEvent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void goBack(ActionEvent actionEvent) {
        try {
            MainApplication.loadScene("customers.fxml", 1050, 500, "", actionEvent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
