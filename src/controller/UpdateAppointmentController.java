package controller;

import helper.AppointmentQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import static helper.AppointmentQuery.*;
import static helper.HelperFunctions.easternTime;
import static model.Main.homeScreen;
import helper.HelperFunctions;
/**This class creates a controller for UpdateAppointment.fxml. */
public class UpdateAppointmentController implements Initializable {
    private ObservableList<Appointment> apptList = FXCollections.observableArrayList();
    public TextField updateAppointmentIdText;
    public TextField updateAppointmentTitleText;
    public TextField updateAppointmentDescriptionText;
    public TextField updateAppointmentLocationText;
    public ComboBox updateAppointmentContactCombo;
    public TextField updateAppointmentTypeText;
    public DatePicker updateAppointmentStart;
    public DatePicker updateAppointmentEnd;
    public ComboBox updateAppointmentStartTimeCombo;
    public ComboBox updateAppointmentEndTimeCombo;
    public ComboBox updateAppointmentCustomerCombo;
    public ComboBox updateAppointmentUserCombo;
    private LocalDateTime s = null;
    private LocalDateTime e = null;

    public static Appointment sA = null;
    public static int appointmentIndex = 0;

    /**
     * The method for passing the selected appointment from the home screen.
     * The selected appointment object and its index are set to variables in the controller for later use.
     * @param selection
     * @param selectedIndex
     */
    public static void passAppointmentSelection(Appointment selection, int selectedIndex){
        sA = selection;
        appointmentIndex = selectedIndex;
    }

    /**
     * The method for preventing overlapping appointments.
     * The current Appointment customer ID, start time, end time, and appointment ID are passed into the method.
     * An observable list is populated with appointments from the database.
     * The list is iterated through so the start and end times of the current appointment can be checked against existing appointments not including the current appointment.
     * @param custId
     * @param start
     * @param end
     * @param apptId
     * @return boolean
     * @throws SQLException
     */
    public boolean appointmentOverlapCheck(int custId, LocalDateTime start, LocalDateTime end, int apptId) throws SQLException {
        apptList = AppointmentQuery.selectAppointmentByCustomer(custId);
        for (int i = 0; i< apptList.size(); i++) {
            s = LocalDateTime.parse(apptList.get(i).getStart(), HelperFunctions.formatter);
            e = LocalDateTime.parse(apptList.get(i).getEnd(), HelperFunctions.formatter);
            boolean case1 = ((start.isAfter(s) || start.isEqual(s))) && (start.isBefore(e));
            boolean case2 = (end.isAfter(s)) && ((end.isBefore(e) || end.isEqual(e)));
            boolean case3 = ((start.isBefore(s) || start.isEqual(s))) && ((end.isAfter(e) || end.isEqual(e)));
            if ((case1 || case2 || case3) && apptList.get(i).getAppointmentId() != apptId) {
                return true;
            }
        }
        return false;
    }

    /**
     * The event handler method for the update button.
     * This method assigns the text field inputs to variables for use in the Appointment class constructor.
     * The method uses various forms of logic to ensure the texts fields are all filled out and use the correct data types, if not a dialogue box is displayed with the error that needs to be corrected.
     * Selected variables are then passed into update() and then homeScreen() is called. The user is directed back to the home screen.
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void onUpdateAppointmentButton(ActionEvent actionEvent) throws IOException, SQLException {
        if(updateAppointmentTitleText.getText() == ""
                || updateAppointmentDescriptionText.getText() == ""
                || updateAppointmentLocationText.getText() == ""
                || updateAppointmentTypeText.getText() == ""
                || updateAppointmentCustomerCombo.getSelectionModel().isEmpty()
                || updateAppointmentUserCombo.getSelectionModel().isEmpty()
                || updateAppointmentContactCombo.getSelectionModel().isEmpty()
                || updateAppointmentStart.getValue() == null
                || updateAppointmentEnd.getValue() == null
                || updateAppointmentStartTimeCombo.getSelectionModel().isEmpty()
                || updateAppointmentEndTimeCombo.getSelectionModel().isEmpty())
        {
            HelperFunctions.errorDialogue("Add Appointment Error", "Please Enter Data in All Text Fields");
            HelperFunctions.error.showAndWait();
        }
        else {
            int appointmentID = (Integer.parseInt(updateAppointmentIdText.getText()));
            String title = updateAppointmentTitleText.getText();
            String description = updateAppointmentDescriptionText.getText();
            String location = updateAppointmentLocationText.getText();
            String type = updateAppointmentTypeText.getText();
            LocalDateTime start = LocalDateTime.of(updateAppointmentStart.getValue(), ((LocalTime) updateAppointmentStartTimeCombo.getSelectionModel().getSelectedItem()));
            LocalDateTime end = LocalDateTime.of(updateAppointmentEnd.getValue(), ((LocalTime) updateAppointmentEndTimeCombo.getSelectionModel().getSelectedItem()));
            int customer = ((Customer) updateAppointmentCustomerCombo.getSelectionModel().getSelectedItem()).getCustomerId();
            int user = ((User) updateAppointmentUserCombo.getSelectionModel().getSelectedItem()).getUserId();
            int contact = ((Contact) updateAppointmentContactCombo.getSelectionModel().getSelectedItem()).getContactId();

            if(end.isBefore(start)){
                HelperFunctions.errorDialogue("Add Appointment Error", "Start Time Must Be Before End Time");
                HelperFunctions.error.showAndWait();
            }
            else {
                if (appointmentOverlapCheck(customer, start, end, appointmentID)) {
                    HelperFunctions.errorDialogue("Update Appointment Error",
                            "Customer " + ((Customer) updateAppointmentCustomerCombo.getSelectionModel().getSelectedItem()) + " Already Has an Appointment for That Time");
                    HelperFunctions.error.showAndWait();
                } else {
                    update(appointmentID, title, description, location, type, start, end, customer, user, contact);
                    homeScreen(actionEvent);
                }
            }
        }
    }

    /**
     * The event handler method for the Cancel button.
     * homeScreen() is called and the user is directed back to the home screen. No appointment is updated.
     * @param actionEvent
     * @throws IOException
     */
    public void onCancelUpdateAppointmentButton(ActionEvent actionEvent) throws IOException {
        homeScreen(actionEvent);
    }

    /**
     * The method for finding a Contact by its ID.
     * selectAllContacts() is called to populate an observable list that is iterated through to compare the passed contact ID and return the appropriate Contact.
     * @param id
     * @return The Contact object corresponding to the passed in ID
     * @throws SQLException
     */
    public Contact findContactById(int id) throws SQLException {
        Contact c = null;
        ObservableList<Contact> contact = helper.ContactQuery.selectAllContacts();
        for (int i = 0; i< contact.size(); i++) {
            if(contact.get(i).getContactId() == id){
                c = contact.get(i);
            }
        }
        return c;
    }

    /**
     * The method for finding a Customer by its ID.
     * selectAllCustomers() is called to populate an observable list that is iterated through to compare the passed customer ID and return the appropriate Customer.
     * @param id
     * @return The Customer object corresponding to the passed in ID
     * @throws SQLException
     */
    public Customer findCustomerById(int id) throws SQLException {
        Customer c = null;
        ObservableList<Customer> customer = helper.CustomerQuery.selectAllCustomers();
        for (int i = 0; i< customer.size(); i++) {
            if(customer.get(i).getCustomerId() == id){
                c = customer.get(i);
            }
        }
        return c;
    }

    /**
     * The method for finding a User by its ID.
     * selectAllUsers() is called to populate an observable list that is iterated through to compare the passed user ID and return the appropriate User.
     * @param id
     * @return The User object corresponding to the passed in ID
     * @throws SQLException
     */
    public User findUserById(int id) throws SQLException {
        User u = null;
        ObservableList<User> user = helper.UserQuery.selectAllUsers();
        for (int i = 0; i< user.size(); i++) {
            if(user.get(i).getUserId() == id){
                u = user.get(i);
            }
        }
        return u;
    }

    /**
     * The first method called when the UpdateAppointmentController is initialized.
     * The combo boxes and text fields are populated with attributes from the selected appointment.
     * A Try/Catch block is used for any SQLExceptions.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            updateAppointmentContactCombo.setItems(helper.ContactQuery.selectAllContacts());
            updateAppointmentCustomerCombo.setItems(helper.CustomerQuery.selectAllCustomers());
            updateAppointmentUserCombo.setItems(helper.UserQuery.selectAllUsers());
            updateAppointmentContactCombo.setValue(findContactById(sA.getContactId()));
            updateAppointmentCustomerCombo.setValue(findCustomerById(sA.getCustomerId()));
            updateAppointmentUserCombo.setValue(findUserById(sA.getUserId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        LocalTime start =HelperFunctions.toLocal(LocalDateTime.of(LocalDate.now(), LocalTime.of(8,0 )), easternTime).toLocalTime();
        LocalTime end = HelperFunctions.toLocal(LocalDateTime.of(LocalDate.now(), LocalTime.of(22,0 )), easternTime).toLocalTime();

        while(start.isBefore(end.plusSeconds(1))){
            updateAppointmentStartTimeCombo.getItems().add(start);
            updateAppointmentEndTimeCombo.getItems().add(start);
            start = start.plusMinutes(30);
        }
        s = LocalDateTime.parse(sA.getStart(), HelperFunctions.formatter);
        e=  LocalDateTime.parse(sA.getEnd(), HelperFunctions.formatter);

        updateAppointmentIdText.setText(Integer.toString(sA.getAppointmentId()));
        updateAppointmentTitleText.setText(sA.getTitle());
        updateAppointmentDescriptionText.setText(sA.getDescription());
        updateAppointmentLocationText.setText(sA.getLocation());
        updateAppointmentTypeText.setText(sA.getType());
        updateAppointmentStart.setValue(s.toLocalDate());
        updateAppointmentEnd.setValue(e.toLocalDate());
        updateAppointmentStartTimeCombo.setValue(s.toLocalTime());
        updateAppointmentEndTimeCombo.setValue(e.toLocalTime());
    }


}
