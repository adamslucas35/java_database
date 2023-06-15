package com.example.c195_aluc167.controllers;

import com.example.c195_aluc167.MainApplication;
import connector.JDBC;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import static connector.JDBC.connection;

/**
 * Window to view reports.
 */
public class ReportsController implements Initializable {

    private TableView<ObservableList<String>> avgTimeTableView;
    @FXML private Pane r_avgTimeTableView;
    ResourceBundle rb = ResourceBundle.getBundle("Lang", Locale.getDefault());
    public ComboBox r_contactCombo;
    private TableView<ObservableList<String>> contactTableView;
    @FXML
    private Pane r_contactTableView;
    @FXML private Pane r_custTableView;
    private TableView<ObservableList<String>> customerTableView;
    @FXML
    private Pane r_timeTableView;
    private TableView<ObservableList<String>> timeTableView;

    /**
     * Initializes reports.fxml and creates tables
     * @param url url
     * @param resourceBundle resourceBundles
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PreparedStatement getData = null;
        try {
            getData = JDBC.connection.prepareStatement("SELECT Contact_Name FROM contacts");
            ResultSet rs = getData.executeQuery();
            r_contactCombo.getItems().clear();
            while (rs.next()) {
                r_contactCombo.getItems().add(rs.getString("Contact_Name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String customerQuery = "SELECT MONTHNAME(a.Start) as `%s`, a.Type as `%s`, COUNT(*) as `%s` " +
                "FROM appointments a " +
                "INNER JOIN customers c ON c.Customer_ID = a.Customer_ID " +
                "GROUP BY a.Type, MONTHNAME(a.Start)";
        String month = rb.getString("month");
        String type = rb.getString("appointmentType");
        String count = rb.getString("count");
        String formatCustomerQuery = String.format(customerQuery, month, type, count);

        try {
            loadCustomerData(formatCustomerQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        String query1 = "SELECT Appointment_ID as '%s', TIME(Start) as '%s', TIME(End) '%s', AVG(timestampdiff(MINUTE, Start, End)) AS '%s' FROM appointments GROUP BY TIME(Start), TIME(End), Appointment_ID";
        String appointmentId = rb.getString("appointmentID");
        String startT = rb.getString("appointmentStart");
        String endT = rb.getString("appointmentEnd");
        String duration = rb.getString("duration");
        String query2 = "SELECT AVG(TIMESTAMPDIFF(MINUTE, Start, End)) AS '%s'" +
                "FROM appointments;";
        String avgDur = rb.getString("avgDuration");
        query2 = String.format(query2, avgDur);


        query1 = String.format(query1, appointmentId, startT, endT, duration);
        try {
            timeTable(query1, query2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * function creates layout for contactTable
     * @param rs ResourceBundle Lang
     * @throws SQLException throws error in case of bad SQL query
     */
    public void createContactTable(ResultSet rs) throws SQLException {
        contactTableView = new TableView<>();

        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            TableColumn<ObservableList<String>, String> tableColumn = new TableColumn<>(rs.getMetaData().getColumnName(i));
            final int columnIndex = i;
            tableColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().get(columnIndex - 1)));
            contactTableView.getColumns().add(tableColumn);
            tableColumn.setPrefWidth(100);
        }
        TableColumn<ObservableList<String>, ?> appointmentIDColumn = contactTableView.getColumns().get(0);
        TableColumn<ObservableList<String>, ?> titleColumn = contactTableView.getColumns().get(1);
        TableColumn<ObservableList<String>, ?> typeColumn = contactTableView.getColumns().get(2);
        TableColumn<ObservableList<String>, ?> descriptionColumn = contactTableView.getColumns().get(3);
        TableColumn<ObservableList<String>, ?> startColumn = contactTableView.getColumns().get(4);
        TableColumn<ObservableList<String>, ?> endColumn = contactTableView.getColumns().get(5);
        TableColumn<ObservableList<String>, ?> customerIDColumn = contactTableView.getColumns().get(6);
        String appointment = rb.getString("appointmentID");
        String title = rb.getString("appointmentTitle");
        String description = rb.getString("appointmentDescription");
        String type = rb.getString("appointmentType");
        String start = rb.getString("appointmentStart");
        String end = rb.getString("appointmentEnd");
        String customerId = rb.getString("appointmentCustomerID");
        appointmentIDColumn.setText(appointment);
        titleColumn.setText(title);
        typeColumn.setText(type);
        descriptionColumn.setText(description);
        startColumn.setText(start);
        endColumn.setText(end);
        customerIDColumn.setText(customerId);

        contactTableView.setPrefWidth(700);
        contactTableView.setPrefHeight(120);
    }

    /**
     * Load data from query to table
     * @param query SQL query
     * @throws SQLException throws error in case of bad SQL query
     */
    public void loadContactInfo(String query)
    {
        try {
            PreparedStatement loadContact = JDBC.connection.prepareStatement(query);
            ResultSet rs = loadContact.executeQuery();

            createContactTable(rs);
            while (rs.next())
            {
                ObservableList<String> rowData = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
                {
                    if (i == 5 || i == 6)
                    {
                        LocalDateTime utcDateTime = LocalDateTime.parse(rs.getString(i), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        LocalDateTime localDateTime = JDBC.convertUTCtoLocal(utcDateTime);
                        rowData.add(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    }
                    else
                    {
                        rowData.add(rs.getString(i));
                    }
                }
                contactTableView.getItems().add(rowData);
            }

            r_contactTableView.getChildren().add(contactTableView);

        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
    /**
     * function creates layout for customerTable
     * @param rs ResourceBundle Lang
     * @throws SQLException throws error in case of bad SQL query
     */
    public void generateCustomerTable(ResultSet rs) throws SQLException {
        customerTableView = new TableView<>();
        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
        {
            TableColumn<ObservableList<String>, String> custTableColumn = new TableColumn<>(rs.getMetaData().getColumnName(i));
            final int columnIndex = i;
            custTableColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().get(columnIndex - 1)));
            customerTableView.setPrefWidth(500);
            customerTableView.setPrefHeight(130);
            custTableColumn.setPrefWidth(120);
            customerTableView.getColumns().add(custTableColumn);
        }
    }

    /**
     * Load data from query to table
     * @param query SQL query
     * @throws SQLException throws error in case of bad SQL query
     */
    public void loadCustomerData(String query) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        generateCustomerTable(rs);
        while (rs.next())
        {
            ObservableList<String> rowData = FXCollections.observableArrayList();
            for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
            {
                rowData.add(rs.getString(i));
            }
            customerTableView.getItems().add(rowData);

        }
        r_custTableView.getChildren().add(customerTableView);
    }


    /**
     * Return to load.fxml
     * @param actionEvent when button is clicked
     * @throws IOException throws input output error
     */
    public void return_to_load(ActionEvent actionEvent) throws IOException {
        MainApplication.return_to_load(actionEvent);
    }

    /**
     * When combo box is pressed show table
     * @param actionEvent when combo box is clicked
     * @throws SQLException throws error in case of bad SQL query
     */
    public void r_contactComboReleased(ActionEvent actionEvent) throws SQLException {
        String contactID = null;
        PreparedStatement contactId = connection.prepareStatement("SELECT Contact_ID from contacts WHERE Contact_Name = ?");
        contactId.setString(1, (String)r_contactCombo.getValue());
        ResultSet rs = contactId.executeQuery();
        while (rs.next())
        {
            contactID = rs.getString("Contact_ID");
        }
        String query = "SELECT Appointment_ID, Title, Type, Description, Start, End, Customer_ID FROM appointments WHERE Contact_ID = " + contactID;
        loadContactInfo(query);
    }

    /**
     * function creates layout for timeTables
     * @param rs ResourceBundle Lang
     * @throws SQLException throws error in case of bad SQL query
     */
    public void generateTimeTable(ResultSet rs, ResultSet rs2) throws SQLException {
        timeTableView = new TableView<>();
        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            TableColumn<ObservableList<String>, String> timeTableColumn = new TableColumn<>(rs.getMetaData().getColumnName(i));
            final int columnIndex = i;
            timeTableColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().get(columnIndex - 1)));
            timeTableView.setPrefWidth(310);
            timeTableView.setPrefHeight(130);
            timeTableView.getColumns().add(timeTableColumn);
        }

        avgTimeTableView = new TableView<>();
        for (int i = 1; i <= rs2.getMetaData().getColumnCount(); i++) {
            TableColumn<ObservableList<String>, String> avgTimeTableColumn = new TableColumn<>(rs2.getMetaData().getColumnName(i));
            final int columnIndex = i;
            avgTimeTableColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().get(columnIndex - 1)));
            avgTimeTableView.setPrefWidth(120);
            avgTimeTableView.setPrefHeight(75);
            avgTimeTableView.getColumns().add(avgTimeTableColumn);
        }


    }


    /**
     * Load data from query to table.
     * @param query1 SQL query
     * @param query2 SQL query
     * @throws SQLException throws error in case of bad SQL query
     */
    public void timeTable(String query1, String query2) throws SQLException {

        PreparedStatement ps = connection.prepareStatement(query1);
        ResultSet rs = ps.executeQuery();
        PreparedStatement ps2 = connection.prepareStatement(query2);
        ResultSet rs2 = ps2.executeQuery();
        generateTimeTable(rs, rs2);
        while (rs.next())
        {
            ObservableList<String> rowData = FXCollections.observableArrayList();
            for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
            {
                rowData.add(rs.getString(i));
            }
            timeTableView.getItems().add(rowData);

        }
        r_timeTableView.getChildren().add(timeTableView);

        while(rs2.next())
        {
            ObservableList<String> rowData2 = FXCollections.observableArrayList();
            rowData2.add(rs2.getString(1));
            avgTimeTableView.getItems().add(rowData2);
        }
        r_avgTimeTableView.getChildren().add(avgTimeTableView);
    }

}
