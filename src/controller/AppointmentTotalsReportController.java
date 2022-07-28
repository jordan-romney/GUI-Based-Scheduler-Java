package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.AppointmentType;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Month;
import java.util.ResourceBundle;
import static helper.AppointmentQuery.*;
/**This class creates a controller for AppointmentTotalsReport.fxml. */
public class AppointmentTotalsReportController implements Initializable {
    public ComboBox appointmentTypeCombo;
    public ComboBox dateCombo;
    public Label appointmentsLabel;
    public Label numAppointmentLabel;
    private ObservableList months = FXCollections.observableArrayList(java.time.Month.values());

    /**
     * The event handler method for the Show ALl button.
     * The combo boxes and numAppointmentLabel are reset to show total appointments.
     * @param actionEvent
     * @throws SQLException
     */
    public void onShowAllButton(ActionEvent actionEvent) throws SQLException {
        appointmentTypeCombo.getSelectionModel().clearSelection();
        dateCombo.getSelectionModel().clearSelection();
        numAppointmentLabel.setText(Integer.toString(selectAllAppointment()));
    }

    /**
     * The method for setting the display text.
     * Various logic is used to determine the combo box selections and sets label text accordingly.
     */
    public void setAppointmentsLabel(){
        if(appointmentTypeCombo.getSelectionModel().getSelectedItem() == null && dateCombo.getSelectionModel().getSelectedItem() == null) {
            appointmentsLabel.setText("Total Appointments");
        }
        else {
            if (appointmentTypeCombo.getSelectionModel().isEmpty()) {
                appointmentsLabel.setText("Total Number of Appointments in " + dateCombo.getSelectionModel().getSelectedItem().toString() + ":");
            } else if (dateCombo.getSelectionModel().isEmpty()) {
                appointmentsLabel.setText("Total Number of " + appointmentTypeCombo.getSelectionModel().getSelectedItem().toString() + " Appointments:");
            } else {
                appointmentsLabel.setText("Total Number of " + appointmentTypeCombo.getSelectionModel().getSelectedItem().toString() + " Appointments in " + dateCombo.getSelectionModel().getSelectedItem().toString() + ":");
            }
        }
    }

    /**
     * The method for displaying the calculated number of appointments.
     * Various logic is used to determine the combo box selections and sets label text accordingly via selectAppointmentByMonth(), selectAppointmentByType(), and selectAppointmentByTypeAndMonth().
     * @throws SQLException
     */
    public void setNumAppointmentLabel() throws SQLException {
        if(appointmentTypeCombo.getSelectionModel().getSelectedItem() == null && dateCombo.getSelectionModel().getSelectedItem() == null) {
            numAppointmentLabel.setText("0");
        }
        else {
            if (appointmentTypeCombo.getSelectionModel().isEmpty()) {
                numAppointmentLabel.setText(Integer.toString(selectAppointmentByMonth(((Month) dateCombo.getSelectionModel().getSelectedItem()).getValue())));
            } else if (dateCombo.getSelectionModel().isEmpty()) {
                numAppointmentLabel.setText(Integer.toString(selectAppointmentByType(((AppointmentType) appointmentTypeCombo.getSelectionModel().getSelectedItem()).getType())));
            } else {
                numAppointmentLabel.setText(Integer.toString(selectAppointmentByTypeAndMonth(((AppointmentType) appointmentTypeCombo.getSelectionModel().getSelectedItem()).getType(),
                        ((Month) dateCombo.getSelectionModel().getSelectedItem()).getValue())));
            }
        }
    }

    /**
     * The event handler method for the Appointment Type combo box.
     * setAppointmentsLabel() and setNumAppointmentLabel() are called to displayed appropriate labels.
     * @param actionEvent
     * @throws SQLException
     */
    public void onAppointmentTypeCombo(ActionEvent actionEvent) throws SQLException {
        setAppointmentsLabel();
        setNumAppointmentLabel();
    }

    /**
     * The event handler method for the Date combo box.
     * setAppointmentsLabel() and setNumAppointmentLabel() are called to displayed appropriate labels.
     * @param actionEvent
     * @throws SQLException
     */
    public void onDateCombo(ActionEvent actionEvent) throws SQLException {
        setAppointmentsLabel();
        setNumAppointmentLabel();
    }

    /**
     * The event handler method for the close button.
     * The user is directed back to the Report Selection page.
     * @param actionEvent
     * @throws IOException
     */
    public void onCloseButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportSelection.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Report Selection");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The first method called when the AppointmentTotalsReportController is initialized.
     * This method sets the data that will populate the combo boxes on the form.
     * A Try/Catch block is used for any SQLExceptions.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointmentTypeCombo.setItems(helper.AppointmentQuery.selectAppointmentTypes());
            dateCombo.setItems(months);
            numAppointmentLabel.setText(Integer.toString(selectAllAppointment()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
