package com.example.c195_aluc167.controllers;

import com.example.c195_aluc167.MainApplication;
import javafx.beans.value.ObservableLongValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import static connector.JDBC.connection;

/**
 * Form to create customer.
 */
public class CreateCustomerController implements Initializable
{

    public ComboBox cc_divisionCombo;
    public ComboBox cc_countryCombo;
    public TextField cc_customerPhone_tf;
    public TextField cc_customerPostal_tf;
    public TextField cc_customerAddress_tf;
    public TextField cc_customerName_tf;
    public TextField cc_customerID_tf;

    ResourceBundle rb = ResourceBundle.getBundle("Lang", Locale.getDefault());

    /**
     * Initializes createCustomer.fxml and sets Customer ID to disable.
     * @param url url
     * @param resourceBundle resourceBundles
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        String auto = rb.getString("autoGen");
        cc_customerID_tf.setDisable(true);
        cc_customerID_tf.setEditable(false);
        cc_customerID_tf.setText(auto);
    }

    /**
     * When submit button is clicked, runs query to insert new customer into database.
     * @param actionEvent when button is clicked.
     * @throws SQLException throws error if sql query is not correct
     * @throws IOException throws input output error
     */
    public void onSubmit(ActionEvent actionEvent) throws SQLException, IOException {
        String divisionID = null;
        String ds = null, cs = null;

        PreparedStatement countryCheck = connection.prepareStatement("SELECT Country_ID FROM countries WHERE Country = ?");
        countryCheck.setString(1, (String) cc_countryCombo.getValue());
        ResultSet countrySet = countryCheck.executeQuery();
        while(countrySet.next())
        {
            cs = countrySet.getString("Country_ID");
        }

        PreparedStatement divisionCheck = connection.prepareStatement("SELECT Country_ID FROM first_level_divisions WHERE Division = ?");
        divisionCheck.setString(1, (String) cc_divisionCombo.getValue());
        ResultSet divisionSet = divisionCheck.executeQuery();
        while(divisionSet.next())
        {
            ds = divisionSet.getString("Country_ID");
        }
        String empty = rb.getString("isEmpty");

        if (!(Objects.equals(cs, ds)))
        {
            String noMatch = rb.getString("noMatch");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(noMatch);
            alert.showAndWait();
        }
        else if (cc_customerName_tf.getText().isEmpty())
        {
            String cName = rb.getString("customerName");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(cName + " " + empty);
            alert.showAndWait();
        }
        else if (cc_customerAddress_tf.getText().isEmpty())
        {
            String cAddress = rb.getString("customerAddress");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(cAddress + " " + empty);
            alert.showAndWait();
        }
        else if (cc_customerPhone_tf.getText().isEmpty())
        {
            String cPhone = rb.getString("customerPhone");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(cPhone + " " + empty);
            alert.showAndWait();
        }
        else if (cc_countryCombo.getItems().isEmpty())
        {
            String c = rb.getString("country");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(c + " " + empty);
            alert.showAndWait();
        }
        else if (cc_divisionCombo.getItems().isEmpty())
        {
            String div = rb.getString("division");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(div + " " + empty);
            alert.showAndWait();
        }
        else
        {
            PreparedStatement ps = connection.prepareStatement("SELECT Division_ID FROM first_level_divisions WHERE Division = ?");
            ps.setString(1, (String) cc_divisionCombo.getValue());
            ResultSet dSet =  ps.executeQuery();

            PreparedStatement createStatement = connection.prepareStatement("INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) " +
                    "VALUES (?, ?, ?, ?, ?);");
            createStatement.setString(1, cc_customerName_tf.getText());
            createStatement.setString(2, cc_customerAddress_tf.getText());
            createStatement.setString(3, cc_customerPostal_tf.getText());
            createStatement.setString(4, cc_customerPhone_tf.getText());
            while(dSet.next())
            {
                createStatement.setString(5, dSet.getString("Division_ID"));
            }
            createStatement.executeUpdate();


            MainApplication.loadScene("customers.fxml", 1050, 500, "", actionEvent);

        }

    }
    /**
     * Return to customers.fxml
     * @param actionEvent when button is clicked.
     */
    public void goBack(ActionEvent actionEvent)
    {
        try {
            MainApplication.loadScene("customers.fxml", 1050, 500, "", actionEvent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * When combo box is clicked, runs query to display country names.
     * @param mouseEvent when combo box is clicked.
     */
    public void  onCountryComboOpened(MouseEvent mouseEvent) {
        try {
            PreparedStatement loadCountries = connection.prepareStatement("SELECT Country FROM countries");
            ResultSet rs = loadCountries.executeQuery();
            cc_countryCombo.getItems().clear();
            while (rs.next()) {
                cc_countryCombo.getItems().add(rs.getString("Country"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * When combo box is clicked, runs query to display division names based on country selected.
     * @param mouseEvent when combo box is clicked.
     */
    public void onDivisionComboOpened(MouseEvent mouseEvent)
    {
        if(cc_countryCombo.getValue() == null)
        {
            String chooseCountry = rb.getString("chooseCountry");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(chooseCountry);
            alert.showAndWait();
        }
        else {
            try {
                PreparedStatement loadDivisions = connection.prepareStatement("SELECT f.Division FROM first_level_divisions f INNER JOIN countries c ON c.Country_ID = f.Country_ID WHERE c.Country = ?");
                loadDivisions.setString(1, (String) cc_countryCombo.getValue());
                ResultSet load = loadDivisions.executeQuery();

                cc_divisionCombo.getItems().clear();

                while (load.next()) {
                    cc_divisionCombo.getItems().add(load.getString("Division"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }




}
