package com.example.c195_aluc167.controllers;

import com.example.c195_aluc167.MainApplication;
import connector.JDBC;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;



/**
 * Controls customers.fxml file
 */
public class CustomersController implements Initializable  {

    public AnchorPane cs_tableView;
    @FXML private TextField cs_searchBar;

    private TableView<ObservableList<String>> customerTableView;
    private ObservableList<ObservableList<String>> customerData;
    private FilteredList<ObservableList<String>> filteredCustomers;

    private String q_selectAllCustomers = "SELECT * FROM customers";


    public void loadFullTable()
    {
        customerTableView = new TableView<>();

        customerData = FXCollections.observableArrayList();
        try {
            PreparedStatement loadCustomer = JDBC.connection.prepareStatement(q_selectAllCustomers);
            ResultSet rs = loadCustomer.executeQuery();

            // Create table columns based on SQL columns
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                TableColumn<ObservableList<String>, String> tableColumn = new TableColumn<>(rs.getMetaData().getColumnName(i));
                final int columnIndex = i;
                tableColumn.setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().get(columnIndex - 1)));
                customerTableView.getColumns().add(tableColumn);
            }



            //populate with data
            while (rs.next()) {
                ObservableList<String> rowData = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    rowData.add(rs.getString(i));
                }
                customerTableView.getItems().add(rowData);
            }


            cs_tableView.getChildren().add(customerTableView);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




    }

    /**
     * Initialization of customers.fxml
     * @param url url
     * @param resourceBundle looking for resource bundle.
     */
    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadFullTable();
        System.out.println("Customer page has been initialized!");

    }


    public void cs_return_to_load(ActionEvent actionEvent) throws IOException {
        MainApplication.return_to_load(actionEvent);
    }

    public void cs_searchKeyPressed(KeyEvent keyEvent) {
        String searchedName = cs_searchBar.getText().toLowerCase();

        if (searchedName.isEmpty()) {
            loadFullTable();
        } else {
            try {
                PreparedStatement loadSearch = JDBC.connection.prepareStatement("SELECT * FROM customers WHERE Customer_Name LIKE ?");
                loadSearch.setString(1, "%" + searchedName + "%");
                ResultSet ls = loadSearch.executeQuery();
                customerTableView.getItems().clear();
                Boolean noQuery = false;
                    while (ls.next()) {
                        noQuery = true;
                        ObservableList<String> rowData = FXCollections.observableArrayList();
                        for (int i = 1; i <= ls.getMetaData().getColumnCount(); i++) {
                            rowData.add(ls.getString(i));
                        }
                        customerTableView.getItems().add(rowData);
                    }
                    if (!noQuery)
                    {
                        ResourceBundle rb = ResourceBundle.getBundle("Lang", Locale.getDefault());
                        String queryNotFound = rb.getString("no_query");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText(queryNotFound);
                        alert.showAndWait();
                    }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public void cs_createCustomerClicked(ActionEvent actionEvent)
    {
        //TODO, WELL CREATE CUSTOMER


    }


    public void cs_modifyCustomerClicked(ActionEvent actionEvent) {
        ObservableList<String> selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        String customerId = selectedCustomer.get(0);
        String customerName = selectedCustomer.get(1);
        String customerAddress = selectedCustomer.get(2);
        String customerPostal = selectedCustomer.get(3);
        String customerPhone = selectedCustomer.get(4);
    }







}
