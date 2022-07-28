package controller;

import helper.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;
import java.util.ResourceBundle;
/**This class creates a controller for HomeScreen.fxml. */
public class HomeScreenController implements Initializable {
    public TableColumn customerCountry;
    private ObservableList<Appointment> allAppts =  FXCollections.observableArrayList();
    public TableView allAppointmentsTable;
    public TableView allCustomersTable;
    public TableColumn customerId;
    public TableColumn customerName;
    public TableColumn customerAddress;
    public TableColumn customerFLD;
    public TableColumn customerPostal;
    public TableColumn customerPhone;
    public TableColumn appointmentId;
    public TableColumn title;
    public TableColumn description;
    public TableColumn location;
    public TableColumn contact;
    public TableColumn type;
    public TableColumn start;
    public TableColumn end;
    public TableColumn appointmentCustomerId;
    public TableColumn userId;
    public Appointment selectedAppointment= null;
    public Customer selectedCustomer= null;
    private LocalDateTime s = null;
    /**
     * A lambda expression that loads a scene.
     * The path and title of the scene are passed in to load a new scene.
     */
    SceneLoader sceneLoader = (String path, String title, ActionEvent actionEvent) -> {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(path));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    };

    /**
     * The event handler for the Log Out button.
     * The sceneLoader lambda expression is used to direct the user back to the Log In screen.
     * @param actionEvent
     * @throws IOException
     */
    public void onLogOutButton(ActionEvent actionEvent) throws IOException {
        sceneLoader.setScene("/view/LoginScreen.fxml", "Log In", actionEvent );
    }

    /**
     * The event handler for the Add Appointment button.
     * The sceneLoader lambda expression is used to direct the user to the Add Appointment screen.
     * @param actionEvent
     * @throws IOException
     */
    public void onAppointmentAdd(ActionEvent actionEvent) throws IOException {
        sceneLoader.setScene("/view/AddAppointment.fxml", "Add Appointment", actionEvent );
    }

    /**
     * The event handler for the Update Appointment button.
     * The selected appointment and its index are passed into passAppointmentSelection().
     * An error message is displayed if the selected appointment is null
     * The sceneLoader lambda expression is used to direct the user to the Update Appointment screen.
     * @param actionEvent
     * @throws IOException
     */
    public void onAppointmentUpdate(ActionEvent actionEvent) throws IOException {
        selectedAppointment = ((Appointment)allAppointmentsTable.getSelectionModel().getSelectedItem());
        int selectedIndex = allAppointmentsTable.getSelectionModel().getSelectedIndex();
        UpdateAppointmentController.passAppointmentSelection(selectedAppointment, selectedIndex);
        HelperFunctions.errorDialogue("Update Error", "No Appointment Selected");
        if (selectedAppointment == null) {
            HelperFunctions.error.showAndWait();
        }
        else {
            sceneLoader.setScene("/view/UpdateAppointment.fxml", "Update Appointment", actionEvent );
        }
    }

    /**
     * The event handler method for the Delete Appointment button.
     * Dialogue boxes appear to confirm the Delete, alert the user if the selected appointment is null, and confirm which appointment was deleted
     * The appointment is removed from the database.
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void onAppointmentDelete(ActionEvent actionEvent) throws SQLException, IOException {
        selectedAppointment = (Appointment)allAppointmentsTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete selected Appointment?");
        Optional<ButtonType> result = alert.showAndWait();

        HelperFunctions.errorDialogue("Delete Error", "No Data to Delete");

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (selectedAppointment == null) {
                HelperFunctions.error.showAndWait();
            }
            else{
                AppointmentQuery.delete(selectedAppointment.getAppointmentId());
                AppointmentQuery.removeAppointmentByObj(selectedAppointment);
                HelperFunctions.informDialogue("Appointment Deleted",
                        "The Following Appointment Was Successfully Deleted: \n Appointment ID: " + selectedAppointment.getAppointmentId() + "\n" + "Appointment Type: " + selectedAppointment.getType());
                HelperFunctions.inform.showAndWait();
            }
        }
    }

    /**
     * The event handler for the Add Customer button.
     * The sceneLoader lambda expression is used to direct the user to the Add Customer screen.
     * @param actionEvent
     * @throws IOException
     */
    public void onCustomerAdd(ActionEvent actionEvent) throws IOException {
        sceneLoader.setScene("/view/AddCustomer.fxml", "Add Customer", actionEvent );
    }

    /**
     * The event handler for the Update Customer button.
     * The selected customer and its index are passed into passCustomerSelection().
     * An error message is displayed if the selected customer is null
     * The sceneLoader lambda expression is used to direct the user to the Update Appointment screen.
     * @param actionEvent
     * @throws IOException
     */
    public void onCustomerUpdate(ActionEvent actionEvent) throws IOException {
        selectedCustomer = ((Customer)allCustomersTable.getSelectionModel().getSelectedItem());
        int selectedIndex = allCustomersTable.getSelectionModel().getSelectedIndex();
        UpdateCustomerController.passCustomerSelection(selectedCustomer, selectedIndex);

        HelperFunctions.errorDialogue("Update Error", "No Customer Selected");

        if (selectedCustomer == null) {
            HelperFunctions.error.showAndWait();
        }
        else {
            sceneLoader.setScene("/view/UpdateCustomer.fxml", "Update Customer", actionEvent);
        }
    }

    /**
     * The event handler method for the Delete Customer button.
     * Dialogue boxes appear to confirm the Delete, alert the user if the selected customer is null, and confirm which customer was deleted
     * The customer is removed from the database.
     * @param actionEvent
     * @throws SQLException
     */
    public void onCustomerDelete(ActionEvent actionEvent) throws SQLException {
        selectedCustomer = (Customer) allCustomersTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete selected Customer?");
        Optional<ButtonType> result = alert.showAndWait();

        HelperFunctions.errorDialogue("Delete Error", "No Data to Delete");

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (selectedCustomer == null) {
                HelperFunctions.error.showAndWait();
            }
            else{
                int customerId = selectedCustomer.getCustomerId();
                ObservableList<Appointment> appointments = AppointmentQuery.selectAllAppointmentAdd();
                for (int i = 0; i< appointments.size(); i++) {
                    if(appointments.get(i).getUserId() == customerId){
                        AppointmentQuery.delete(appointments.get(i).getAppointmentId());
                    }
                }
                CustomerQuery.delete(customerId);
                CustomerQuery.removeCustomerByObj(selectedCustomer);
                allAppointmentsTable.setItems(AppointmentQuery.selectAllAppointmentAdd());
                HelperFunctions.informDialogue("Customer Deleted", selectedCustomer.getCustomerName() + " Was Successfully Deleted");
                HelperFunctions.inform.showAndWait();
            }
        }
    }

    /**
     * The event handler method for the All Appointments radio.
     * The allAppointmentsTable is populated with all the appointments in the allAppts list.
     * @param actionEvent
     */
    public void onAllAppointmentRadio(ActionEvent actionEvent) {
        allAppointmentsTable.setItems(allAppts);
    }

    /**
     * The event handler method for the Monthly Appointments radio.
     * A new observable list is populated by iterating through the allAppts list and comparing the start date to the current month.
     * The allAppointmentsTable is populated with appointments in the monthlyAppointments list.
     * @param actionEvent
     */
    public void onMonthlyAppointmentRadio(ActionEvent actionEvent) {
        ObservableList<Appointment> monthlyAppointments = FXCollections.observableArrayList();

        for (int i = 0; i< allAppts.size(); i++) {
            s = LocalDateTime.parse(allAppts.get(i).getStart(), HelperFunctions.formatter);
            if(s.getMonthValue() == LocalDateTime.now().getMonthValue() &&
                    s.getYear() == LocalDateTime.now().getYear()){
                monthlyAppointments.add(allAppts.get(i));
            }
        }
        allAppointmentsTable.setItems(monthlyAppointments);
    }

    /**
     * The event handler method for the Weekly Appointments radio.
     * A new observable list is populated by iterating through the allAppts list and comparing the start date to the previous Sunday and last Saturday.
     * The allAppointmentsTable is populated with the appointments in the weeklyAppointments list.
     * @param actionEvent
     */
    public void onWeeklyAppointmentRadio(ActionEvent actionEvent) {
        ObservableList<Appointment> weeklyAppointments = FXCollections.observableArrayList();
        LocalDateTime sunday = LocalDateTime.now().with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
        LocalDateTime saturday = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY));

        for (int i = 0; i< allAppts.size(); i++) {
            s = LocalDateTime.parse(allAppts.get(i).getStart(), HelperFunctions.formatter);
            if(sunday.isBefore(s) && saturday.isAfter(s)) {
                weeklyAppointments.add(allAppts.get(i));
            }
        }
        allAppointmentsTable.setItems(weeklyAppointments);
    }

    /**
     * The event handler for the Report Selection button.
     * The sceneLoader lambda expression is used to direct the user to the Report Selection screen.
     * @param actionEvent
     * @throws IOException
     */
    public void onReportSelectionButton(ActionEvent actionEvent) throws IOException {
        sceneLoader.setScene("/view/ReportSelection.fxml", "Report Selection", actionEvent );
    }

    /**
     * The first method called when the HomeScreenController is initialized.
     * This method sets the data that will populate the allCustomersTable and allAppointmentsTable.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        try {
            allAppts = AppointmentQuery.selectAllAppointmentAdd();
            allCustomersTable.setItems(CustomerQuery.selectAllCustomers());
            allAppointmentsTable.setItems(AppointmentQuery.selectAllAppointmentAdd());
        } catch (SQLException e) {
            e.printStackTrace();
        }


        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerFLD.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        customerCountry.setCellValueFactory(new PropertyValueFactory<>("countryId"));
        customerPostal.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));


        appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        type.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("type"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));
        appointmentCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userId.setCellValueFactory(new PropertyValueFactory<>("userId"));

    }
}