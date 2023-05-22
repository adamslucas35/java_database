package com.example.c195_aluc167.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import model.Customers;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyCustomerController implements Initializable {

    @FXML private TextField mc_customerID_tf;
    @FXML private TextField mc_customerName_tf;
    @FXML private TextField mc_customerAddress_tf;
    @FXML private TextField mc_customerPostal_tf;
    @FXML private TextField mc_customerPhone_tf;




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
}
