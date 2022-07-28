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

/**This class creates a controller for AddAppointment.fxml. */
public class AddAppointmentController implements Initializable {
    private ObservableList<Appointment> apptList = FXCollections.observableArrayList();
    public TextField addAppointmentIdText;
    public TextField addAppointmentTitleText;
    public TextField addAppointmentDescriptionText;
    public TextField addAppointmentLocationText;
    public ComboBox addAppointmentContactCombo;
    public TextField addAppointmentTypeText;
    public DatePicker addAppointmentStart;
    public DatePicker addAppointmentEnd;
    public ComboBox addAppointmentStartTimeCombo;
    public ComboBox addAppointmentEndTimeCombo;
    public ComboBox addAppointmentCustomerCombo;
    public ComboBox addAppointmentUserCombo;

    /**
     * The method for preventing overlapping appointments.
     * The current Appointment customer ID, start time, and end time are passed into the method.
     * An observable list is populated with appointments from the database.
     * The list is iterated through so the start and end times of the current appointment can be checked against existing appointments.
     * @param custId
     * @param start
     * @param end
     * @return boolean
     * @throws SQLException
     */
    public boolean appointmentOverlapCheck(int custId, LocalDateTime start, LocalDateTime end) throws SQLException {
        apptList = AppointmentQuery.selectAppointmentByCustomer(custId);
        LocalDateTime s = null;
        LocalDateTime e = null;
        for (int i = 0; i< apptList.size(); i++) {
            s = LocalDateTime.parse(apptList.get(i).getStart(), HelperFunctions.formatter);
            e = LocalDateTime.parse(apptList.get(i).getEnd(), HelperFunctions.formatter);
            boolean case1 = ((start.isAfter(s) || start.isEqual(s))) && (start.isBefore(e));
            boolean case2 = (end.isAfter(s)) && ((end.isBefore(e) || end.isEqual(e)));
            boolean case3 = ((start.isBefore(s) || start.isEqual(s))) && ((end.isAfter(e) || end.isEqual(e)));
            if (case1 || case2 || case3) {
                return true;
            }
        }
        return false;
    }

    /**
     * The event handler method for the add button.
     * This method assigns the text field inputs to variables for use in the Appointment class constructor.
     * The method uses various forms of logic to ensure the texts fields are all filled out and use the correct data types, if not a dialogue box is displayed with the error that needs to be corrected.
     * Selected variables are then passed into insert() and  selectAppointmentByStartAdd() and then homeScreen() is called. The user is directed back to the home screen.
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void onAddAppointmentButton(ActionEvent actionEvent) throws SQLException, IOException {
        if(addAppointmentTitleText.getText() == ""
                || addAppointmentDescriptionText.getText() == ""
                || addAppointmentLocationText.getText() == ""
                || addAppointmentTypeText.getText() == ""
                || addAppointmentCustomerCombo.getSelectionModel().isEmpty()
                || addAppointmentUserCombo.getSelectionModel().isEmpty()
                || addAppointmentContactCombo.getSelectionModel().isEmpty()
                || addAppointmentStart.getValue() == null
                || addAppointmentEnd.getValue() == null
                || addAppointmentStartTimeCombo.getSelectionModel().isEmpty()
                || addAppointmentEndTimeCombo.getSelectionModel().isEmpty())
        {

            HelperFunctions.errorDialogue("Add Appointment Error", "Please Enter Data in All Text Fields");
            HelperFunctions.error.showAndWait();
        }
        else {
            String title = addAppointmentTitleText.getText();
            String description = addAppointmentDescriptionText.getText();
            String location = addAppointmentLocationText.getText();
            String type = addAppointmentTypeText.getText();
            LocalDateTime start = LocalDateTime.of(addAppointmentStart.getValue(), ((LocalTime) addAppointmentStartTimeCombo.getSelectionModel().getSelectedItem()));
            LocalDateTime end = LocalDateTime.of(addAppointmentEnd.getValue(), ((LocalTime) addAppointmentEndTimeCombo.getSelectionModel().getSelectedItem()));
            int customer = ((Customer) addAppointmentCustomerCombo.getSelectionModel().getSelectedItem()).getCustomerId();
            int user = ((User) addAppointmentUserCombo.getSelectionModel().getSelectedItem()).getUserId();
            int contact = ((Contact) addAppointmentContactCombo.getSelectionModel().getSelectedItem()).getContactId();

            if(end.isBefore(start)){
                HelperFunctions.errorDialogue("Add Appointment Error","Start Time Must Be Before End Time");
                HelperFunctions.error.showAndWait();
            }
            else {
                if (appointmentOverlapCheck(customer, start, end)) {
                    HelperFunctions.errorDialogue("Add Appointment Error",
                            "Customer " + ((Customer) addAppointmentCustomerCombo.getSelectionModel().getSelectedItem()) + " Already Has an Appointment for That Time");
                    HelperFunctions.error.showAndWait();
                } else {
                    insert(title, description, location, type, start, end, customer, user, contact);
                    selectAppointmentByStartAdd(start);
                    homeScreen(actionEvent);
                }
            }
        }
    }

    /**
     * The event handler method for the Cancel button.
     * homeScreen() is called and the user is directed back to the home screen. No appointment is created.
     * @param actionEvent
     * @throws IOException
     */
    public void onCancelAddAppointmentButton(ActionEvent actionEvent) throws IOException {
        homeScreen(actionEvent);
    }

    /**
     * The first method called when the AddAppointmentController is initialized.
     * This method sets the data that will populate the combo boxes on the form.
     * A Try/Catch block is used for any SQLExceptions.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            addAppointmentContactCombo.setItems(helper.ContactQuery.selectAllContacts());
            addAppointmentCustomerCombo.setItems(helper.CustomerQuery.selectAllCustomers());
            addAppointmentUserCombo.setItems(helper.UserQuery.selectAllUsers());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        LocalTime start =HelperFunctions.toLocal(LocalDateTime.of(LocalDate.now(), LocalTime.of(8,0 )), easternTime).toLocalTime();
        LocalTime end = HelperFunctions.toLocal(LocalDateTime.of(LocalDate.now(), LocalTime.of(22,0 )), easternTime).toLocalTime();

        while(start.isBefore(end.plusSeconds(1))){
          addAppointmentStartTimeCombo.getItems().add(start);
          addAppointmentEndTimeCombo.getItems().add(start);
          start = start.plusMinutes(30);
        }
    }
}
