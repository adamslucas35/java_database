package com.example.c195_aluc167.controllers;

import com.example.c195_aluc167.MainApplication;
import connector.JDBC;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {


    @FXML private ToggleGroup sorter;
    @FXML
    private Label ap_title;
    @FXML
    private Button ap_createAppointment;
    @FXML
    private Button ap_updateAppointment;
    @FXML private Pane ap_tableView;
    @FXML private Button ap_removeAppointment;
    @FXML private Label radioLabel;
    @FXML private RadioButton ap_radioButtonMonth;
    @FXML private RadioButton ap_radioButtonWeek;
    @FXML private RadioButton ap_radioButtonAll;


    private TableView<ObservableList<String>> appointmentTableView;


    ResourceBundle rb = ResourceBundle.getBundle("Lang", Locale.getDefault());

   private String queryLoadAppointments = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, c.Contact_Name, a.Type, a.Start, a.End, a.Customer_ID, a.User_ID  FROM appointments a INNER JOIN contacts c ON c.Contact_ID = a.Contact_ID";
    private ObservableList<Object> appointmentData;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        loadAppointmentTable(queryLoadAppointments);
        //check for appointment within 15 minutes



    }

    public void createAppointmentTable(ResultSet rs) throws SQLException
    {

        appointmentTableView = new TableView<>();
        int insert = appointmentTableView.getColumns().size() - 6;
        TableColumn<ObservableList<String>, String> joinedDataColumn = new TableColumn<>("JoinedData");
        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            TableColumn<ObservableList<String>, String> tableColumn = new TableColumn<>(rs.getMetaData().getColumnName(i));
            final int columnIndex = i;
            tableColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().get(columnIndex - 1)));
            appointmentTableView.setPrefWidth(1025);
            tableColumn.setPrefWidth(100);

            appointmentTableView.getColumns().add(tableColumn);
            if(i == insert)
            {
                appointmentTableView.getColumns().add((joinedDataColumn));
            }
        }

        TableColumn<ObservableList<String>, ?> appointmentIDColumn = appointmentTableView.getColumns().get(0);
        TableColumn<ObservableList<String>, ?> titleColumn = appointmentTableView.getColumns().get(1);
        TableColumn<ObservableList<String>, ?> descriptionColumn = appointmentTableView.getColumns().get(2);
        TableColumn<ObservableList<String>, ?> locationColumn = appointmentTableView.getColumns().get(3);
        TableColumn<ObservableList<String>, ?> typeColumn = appointmentTableView.getColumns().get(4);
        TableColumn<ObservableList<String>, ?> contactNameColumn = appointmentTableView.getColumns().get(5);
        TableColumn<ObservableList<String>, ?> startColumn = appointmentTableView.getColumns().get(6);
        TableColumn<ObservableList<String>, ?> endColumn = appointmentTableView.getColumns().get(7);
        TableColumn<ObservableList<String>, ?> customerIDColumn = appointmentTableView.getColumns().get(8);
        TableColumn<ObservableList<String>, ?> userIDColumn = appointmentTableView.getColumns().get(9);

        String appointment = rb.getString("appointmentID");
        String title = rb.getString("appointmentTitle");
        String description = rb.getString("appointmentDescription");
        String location = rb.getString("appointmentLocation");
        String type = rb.getString("appointmentType");
        String contactName = rb.getString("appointmentContact");
        String start = rb.getString("appointmentStart");
        String end = rb.getString("appointmentEnd");
        String customerId = rb.getString("appointmentCustomerID");
        String userId = rb.getString("appointmentUserID");

        appointmentIDColumn.setText(appointment);
        titleColumn.setText(title);
        descriptionColumn.setText(description);
        locationColumn.setText(location);
        typeColumn.setText(type);
        contactNameColumn.setText(contactName);
        startColumn.setText(start);
        endColumn.setText(end);
        customerIDColumn.setText(customerId);
        userIDColumn.setText(userId);

        startColumn.setPrefWidth(130);
        endColumn.setPrefWidth(130);
        customerIDColumn.setPrefWidth(80);
        userIDColumn.setPrefWidth(70);


    }

    public void loadAppointmentTable(String query)
    {
         appointmentData = FXCollections.observableArrayList();
        try
        {
            String q_selectAllCustomers = query;
            PreparedStatement loadCustomer = JDBC.connection.prepareStatement(q_selectAllCustomers);
            ResultSet rs = loadCustomer.executeQuery();

            // Create table columns based on SQL columns
            createAppointmentTable(rs);
            //populate with data
            while (rs.next())
            {
                ObservableList<String> rowData = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
                {
                    if (i == 7 || i == 8)
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
                appointmentTableView.getItems().add(rowData);
            }

            ap_tableView.getChildren().add(appointmentTableView);


        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

    }

    public void ap_createAppointmentClicked(ActionEvent actionEvent) throws IOException
    {
        MainApplication.loadScene("createAppointment.fxml", 650, 400, "", actionEvent);
    }

    public void ap_modifyAppointmentClicked(ActionEvent actionEvent) throws IOException
    {
        ObservableList<String> selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null)
        {
            String noSelection = rb.getString("noSelection");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(noSelection);
            alert.showAndWait();
        }
        else
        {


            int appointmentID = Integer.parseInt(selectedAppointment.get(0));
            String appointmentTitle = selectedAppointment.get(1);
            String appointmentDescription = selectedAppointment.get(2);
            String appointmentLocation = selectedAppointment.get(3);
            String appointmentContact = selectedAppointment.get(4);
            String appointmentType = selectedAppointment.get(5);

            String appointmentStart = selectedAppointment.get(6);
            LocalDateTime dateTimeStart = LocalDateTime.parse(appointmentStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDate appointmentStartDate = dateTimeStart.toLocalDate();
            LocalTime appointmentStartTime = dateTimeStart.toLocalTime();

            String appointmentEnd = selectedAppointment.get(7);
            LocalDateTime dateTimeEnd = LocalDateTime.parse(appointmentEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDate appointmentEndDate = dateTimeEnd.toLocalDate();
            LocalTime appointmentEndTime = dateTimeEnd.toLocalTime();

            int appCustomerId = Integer.parseInt(selectedAppointment.get(8));
            int appUserID = Integer.parseInt(selectedAppointment.get(9));


            Appointments appointments = new Appointments(appointmentID, appointmentTitle,  appointmentDescription, appointmentLocation, appointmentType, appointmentStartDate, appointmentStartTime, appointmentEndDate, appointmentEndTime, appCustomerId, appUserID);
            Contacts contacts = new Contacts(appointmentContact);

            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("modifyAppointment.fxml"), rb);
            Parent modifyAppointmentRoot = loader.load();
            ModifyAppointmentController modifyAppointmentController = loader.getController();
            modifyAppointmentController.receiveAppointment(appointments, contacts);

            Stage oldStage = (Stage) appointmentTableView.getScene().getWindow();
            oldStage.close();

            Stage newStage = new Stage();
            newStage.setScene(new Scene(modifyAppointmentRoot));
            newStage.show();
        }


    }

    public void ap_removeAppointmentClicked(ActionEvent actionEvent) throws SQLException
    {
        ObservableList<String> selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null)
        {
            String noSelection = rb.getString("noSelection");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(noSelection);
            alert.showAndWait();
        }
        else {
                String appointment = rb.getString("appointment");
                String deletion = rb.getString("deletedAppointment");
                String withType = rb.getString("withType");
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setContentText(appointment + " " + selectedAppointment.get(0) + " " + deletion + " " + withType + " " + selectedAppointment.get(5));
                Optional<ButtonType> result = confirm.showAndWait();
                if (result.get() == ButtonType.OK) {
                    PreparedStatement remove = JDBC.connection.prepareStatement("DELETE FROM appointments WHERE Appointment_ID = ?");
                    remove.setString(1, selectedAppointment.get(0));
                    remove.executeUpdate();
                } else if (result.get() == ButtonType.CANCEL) {
                    System.out.println("CANCELLED");

                } else if (!result.isPresent()) {
                    System.out.println("EXITED");
                }

                loadAppointmentTable(queryLoadAppointments);

        }
    }
    
    public void return_to_load(ActionEvent actionEvent) throws IOException
    {
        MainApplication.return_to_load(actionEvent);
    }

    public void radioMonthClicked(ActionEvent actionEvent)
    {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDayOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());
        String monthSort = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, c.Contact_Name, a.Type, a.Start, a.End, a.Customer_ID, a.User_ID FROM appointments a INNER JOIN contacts c ON c.Contact_ID = a.Contact_ID WHERE Start BETWEEN '"
                + firstDayOfMonth + "' AND '" + lastDayOfMonth + "'";

        loadAppointmentTable(monthSort);
        ObservableList<ObservableList<String>> table = appointmentTableView.getItems();
        if (table.isEmpty())
        {
            String noResult = rb.getString("noMonthSort");
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setContentText(noResult);
            warning.showAndWait();
        }


    }

    public void radioWeekClicked(ActionEvent actionEvent)
    {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate lastDayOfWeek = today.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        String weekSort = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, c.Contact_Name, a.Type, a.Start, a.End, a.Customer_ID, a.User_ID FROM appointments a INNER JOIN contacts c ON c.Contact_ID = a.Contact_ID WHERE Start BETWEEN '"
                + firstDayOfWeek + "' AND '" + lastDayOfWeek + "'";
        loadAppointmentTable(weekSort);

        ObservableList<ObservableList<String>> table = appointmentTableView.getItems();
        if (table.isEmpty())
        {
            String noResult = rb.getString("noWeekSort");
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setContentText(noResult);
            warning.showAndWait();
        }
    }

    public void radioAllClicked(ActionEvent actionEvent)
    {
        loadAppointmentTable(queryLoadAppointments);
    }
}


