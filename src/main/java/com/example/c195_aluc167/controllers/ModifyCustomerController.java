package com.example.c195_aluc167.controllers;

import com.example.c195_aluc167.MainApplication;
import connector.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.Countries;
import model.Customers;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.ResourceBundle;

import static connector.JDBC.connection;

public class ModifyCustomerController implements Initializable {

    @FXML private ComboBox<String> mc_countryCombo;
    @FXML private ComboBox<String> mc_divisionCombo;
    @FXML private TextField mc_customerID_tf;
    @FXML private TextField mc_customerName_tf;
    @FXML private TextField mc_customerAddress_tf;
    @FXML private TextField mc_customerPostal_tf;
    @FXML private TextField mc_customerPhone_tf;


    ResourceBundle rb = ResourceBundle.getBundle("Lang", Locale.getDefault());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        mc_customerID_tf.setDisable(true);
        mc_customerID_tf.setEditable(false);
        System.out.println("Modify customer page has been initialized!");
    }


    public void receiveCustomer(Customers customers, Countries countries) throws SQLException
    {
        Countries country = countries;
        mc_customerID_tf.setText(Integer.toString(customers.getCustomerId()));
        mc_customerName_tf.setText(customers.getCustomerName());
        mc_customerAddress_tf.setText(customers.getCustomerAddress());
        mc_customerPostal_tf.setText(customers.getCustomerPostalCode());
        mc_customerPhone_tf.setText(customers.getCustomerPhone());
        mc_countryCombo.setValue(countries.getCountry());
        mc_divisionCombo.setValue(customers.getCustomerDivision());
    }

    public void  onCountryComboOpened(MouseEvent mouseEvent) {
        try {
            PreparedStatement loadCountries = connection.prepareStatement("SELECT Country FROM countries");
            ResultSet rs = loadCountries.executeQuery();
            mc_countryCombo.getItems().clear();
            while (rs.next()) {
                mc_countryCombo.getItems().add(rs.getString("Country"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void onDivisionComboOpened(MouseEvent mouseEvent)
    {
        if(mc_countryCombo.getValue() == null)
        {
            String chooseCountry = rb.getString("chooseCountry");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(chooseCountry);
            alert.showAndWait();
        }
        else {
            try {
                PreparedStatement loadDivisions = connection.prepareStatement("SELECT f.Division FROM first_level_divisions f INNER JOIN countries c ON c.Country_ID = f.Country_ID WHERE c.Country = ?");
                loadDivisions.setString(1, mc_countryCombo.getValue());
                ResultSet load = loadDivisions.executeQuery();

                mc_divisionCombo.getItems().clear();

                while (load.next()) {
                    mc_divisionCombo.getItems().add(load.getString("Division"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }






    public void onSubmit(ActionEvent actionEvent)
    {
        String divisionID = null;

        try {
            String selectedDivision = mc_divisionCombo.getValue();
            PreparedStatement ps = connection.prepareStatement("SELECT Division_ID from first_level_divisions WHERE Division = ?");
            ps.setString(1, selectedDivision);
            ResultSet rs = ps.executeQuery();

            PreparedStatement updateQuery = connection.prepareStatement("UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?");
            updateQuery.setString(1, mc_customerName_tf.getText());
            updateQuery.setString(2, mc_customerAddress_tf.getText());
            updateQuery.setString(3, mc_customerPostal_tf.getText());
            updateQuery.setString(4, mc_customerPhone_tf.getText());
            while(rs.next())
            {
                updateQuery.setString(5, rs.getString("Division_ID"));
            }
            updateQuery.setString(6, mc_customerID_tf.getText());
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
