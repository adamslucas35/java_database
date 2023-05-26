package com.example.c195_aluc167.controllers;

import com.example.c195_aluc167.MainApplication;
import connector.JDBC;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Countries;
import model.Customers;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;



/**
 * Controls customers.fxml file
 */
public class CustomersController implements Initializable  {

    @FXML private Pane cs_tableView;
    @FXML private Button cs_removeCustomer;
    @FXML private Button cs_updateCustomer;
    @FXML private Button cs_createCustomer;
    @FXML private TextField cs_searchBar;

    private TableView<ObservableList<String>> customerTableView;

    private ObservableList<ObservableList<String>> customerData;

    private String loadData = "SELECT c.Customer_ID, c.Customer_Name, c.Address, c.Postal_Code, c.Phone, ct.Country, f.Division " +
            "FROM customers c " +
            "INNER JOIN first_level_divisions f ON f.Division_ID = c.Division_ID " +
            "INNER JOIN countries ct ON ct.Country_ID = f.Country_ID";


    ResourceBundle rb = ResourceBundle.getBundle("Lang", Locale.getDefault());

    public void createTable(ResultSet rs) throws SQLException {
        customerTableView = new TableView<>();
        int insert = customerTableView.getColumns().size() - 1;
        TableColumn<ObservableList<String>, String> joinedDataColumn = new TableColumn<>("JoinedData");
        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            TableColumn<ObservableList<String>, String> tableColumn = new TableColumn<>(rs.getMetaData().getColumnName(i));
            final int columnIndex = i;
            tableColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().get(columnIndex - 1)));
            tableColumn.setPrefWidth(120);
            customerTableView.getColumns().add(tableColumn);
            if (i == insert)
            {
                customerTableView.getColumns().add(joinedDataColumn);
            }
        }
    }

    /**
     * Initialization of customers.fxml
     * @param url url
     * @param resourceBundle looking for resource bundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadFullTable(loadData);
        System.out.println("Customer page has been initialized!");
    }

    public void loadFullTable(String query)
    {


        customerData = FXCollections.observableArrayList();
        try {
            String q_selectAllCustomers = loadData;
            PreparedStatement loadCustomer = JDBC.connection.prepareStatement(q_selectAllCustomers);
            ResultSet rs = loadCustomer.executeQuery();

            // Create table columns based on SQL columns
            createTable(rs);

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


    public void cs_searchKeyPressed(KeyEvent keyEvent) {
        String query = loadData + " WHERE c.Customer_Name LIKE ?";
        String searchedName = cs_searchBar.getText().toLowerCase();
        if (searchedName.isEmpty()) {
            loadFullTable(loadData);
        } else {
            try {
                PreparedStatement loadSearch = JDBC.connection.prepareStatement(query);
                loadSearch.setString(1, "%" + searchedName + "%");
                customerTableView.getItems().clear();
                ResultSet ls = loadSearch.executeQuery();
                Boolean noQuery = false;
                createTable(ls);
                while (ls.next()) {
                    noQuery = true;
                    ObservableList<String> rowData = FXCollections.observableArrayList();
                    for (int i = 1; i <= ls.getMetaData().getColumnCount(); i++) {
                        rowData.add(ls.getString(i));
                    }
                    customerTableView.getItems().add(rowData);
                }
                cs_tableView.getChildren().add(customerTableView);
                if (!noQuery)
                {
                    String queryNotFound = rb.getString("noQuery");
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText(queryNotFound);
                    alert.show();
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


    public void cs_modifyCustomerClicked(ActionEvent actionEvent) throws IOException, SQLException {
        ObservableList<String> selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        int customerId = Integer.parseInt(selectedCustomer.get(0));
        String customerName = selectedCustomer.get(1);
        String customerAddress = selectedCustomer.get(2);
        String customerPostal = selectedCustomer.get(3);
        String customerPhone = selectedCustomer.get(4);
        String customerCountry = selectedCustomer.get(5);
        String customerDivision = selectedCustomer.get(6);

        if (selectedCustomer == null)
        {
            String noSelection = rb.getString("noSelection");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(noSelection);
            alert.show();
        } else
        {
            Customers customers = new Customers(customerId, customerName, customerAddress, customerPostal, customerPhone, customerDivision);
            Countries countries = new Countries(customerCountry);
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("modifyCustomer.fxml"), rb);
            Parent modifyCustomerRoot = loader.load();
            ModifyCustomerController modifyCustomerController = loader.getController();

            modifyCustomerController.receiveCustomer(customers, countries);

            Stage oldStage = (Stage) cs_tableView.getScene().getWindow();
            oldStage.close();

            Stage stage = new Stage();
            stage.setScene(new Scene(modifyCustomerRoot));
            stage.show();
        }

    }

    public void cs_removeCustomerClicked(ActionEvent actionEvent) throws IOException, SQLException {

        ObservableList<String> selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null)
        {
            String noSelection = rb.getString("noSelection");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(noSelection);
            alert.show();
        } else {
            PreparedStatement nameString = JDBC.connection.prepareStatement("SELECT Customer_Name FROM customers WHERE Customer_ID = ?");
            nameString.setString(1, selectedCustomer.get(0));
            String customerName = null;
            ResultSet rs = nameString.executeQuery();
            while (rs.next()) {
                customerName = rs.getString("Customer_Name");
            }
            String delete = rb.getString("deletion");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(customerName + " " + delete);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                PreparedStatement ps = JDBC.connection.prepareStatement("DELETE FROM customers WHERE Customer_ID = ?");
                ps.setString(1, selectedCustomer.get(0));
                ps.executeUpdate();
            } else if (result.get() == ButtonType.CANCEL) {
                System.out.println("CANCELLED");

            } else if (!result.isPresent()) {
                System.out.println("EXITED");
            }

            loadFullTable(loadData);
        }
    }


    public void cs_return_to_load(ActionEvent actionEvent) throws IOException {
        MainApplication.return_to_load(actionEvent);
    }




}
