package com.example.c195_aluc167.controllers;

import com.example.c195_aluc167.MainApplication;
import connector.JDBC;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {


    @FXML
    public Label ap_title;
    @FXML
    public Button ap_createAppointment;
    @FXML
    public Button ap_updateAppointment;
    @FXML public Pane ap_tableView;
    @FXML public Button ap_removeAppointment;
    @FXML public Label radioLabel;
    @FXML public RadioButton ap_radioButtonMonth;
    @FXML public RadioButton ap_radioButtonWeek;

    private TableView<ObservableList<String>> appointmentTableView;


    ResourceBundle rb = ResourceBundle.getBundle("Lang", Locale.getDefault());

   private String queryLoadAppointments = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, c.Contact_Name, a.Type, a.Start, a.End, a.Customer_ID, a.User_ID  FROM appointments a INNER JOIN contacts c ON c.Contact_ID = a.Contact_ID";
    private ObservableList<Object> appointmentData;
//  Appointment_ID - 1
//  Title -2
//  Description - 3
//  Location - 4
//  Contact - 5
//  Type - 6
//  Start Date and Time - 7
//  End Date and Time - 8
//  Customer_ID - 9
//  User_ID - 10

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        loadCustomerTable(queryLoadAppointments);

        ToggleGroup sorter = new ToggleGroup();
        ap_radioButtonMonth.setToggleGroup(sorter);
        ap_radioButtonWeek.setToggleGroup(sorter);
        ap_radioButtonMonth.setSelected(true);
    }

    public void createAppointmentTable(ResultSet rs) throws SQLException {

        int startIndex = 7;
        int endIndex = 8;

        appointmentTableView = new TableView<>();
        int insert = appointmentTableView.getColumns().size() - 6;
        TableColumn<ObservableList<String>, String> joinedDataColumn = new TableColumn<>("JoinedData");
        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            TableColumn<ObservableList<String>, String> tableColumn = new TableColumn<>(rs.getMetaData().getColumnName(i));
            final int columnIndex = i;
            tableColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().get(columnIndex - 1)));
            appointmentTableView.setPrefWidth(950);

            appointmentTableView.getColumns().add(tableColumn);
            if(i == insert)
            {
                appointmentTableView.getColumns().add((joinedDataColumn));
            }
        }
        TableColumn<ObservableList<String>, ?> startTimeColumn = appointmentTableView.getColumns().get(startIndex);
        TableColumn<ObservableList<String>, ?> endTimeColumn = appointmentTableView.getColumns().get(endIndex);
        TableColumn<ObservableList<String>, ?> userIDColumn = appointmentTableView.getColumns().get(9);

        startTimeColumn.setPrefWidth(125);
        endTimeColumn.setPrefWidth(125);
        userIDColumn.setPrefWidth(70);
    }

    public void loadCustomerTable(String query)
    {
         appointmentData = FXCollections.observableArrayList();
        try {
            String q_selectAllCustomers = queryLoadAppointments;
            PreparedStatement loadCustomer = JDBC.connection.prepareStatement(q_selectAllCustomers);
            ResultSet rs = loadCustomer.executeQuery();

            // Create table columns based on SQL columns
            createAppointmentTable(rs);
            //populate with data
            while (rs.next()) {
                ObservableList<String> rowData = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    rowData.add(rs.getString(i));
                }
                appointmentTableView.getItems().add(rowData);
            }

            ap_tableView.getChildren().add(appointmentTableView);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



    public void ap_createAppointmentClicked(ActionEvent actionEvent) throws IOException {
        MainApplication.loadScene("createAppointment.fxml", 650, 400, "", actionEvent);
    }

    public void ap_modifyAppointmentClicked(ActionEvent actionEvent)
    {

    }

    public void ap_removeAppointmentClicked(ActionEvent actionEvent) {
    }

    public void return_to_load(ActionEvent actionEvent) throws IOException
    {
        MainApplication.return_to_load(actionEvent);
    }
}
