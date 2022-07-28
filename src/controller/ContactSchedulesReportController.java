package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Contact;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import static helper.AppointmentQuery.selectAppointmentByContact;
/**This class creates a controller for ContactSchedulesReport.fxml.  */
public class ContactSchedulesReportController implements Initializable {
    public TableColumn appointmentId;
    public TableColumn title;
    public TableColumn type;
    public TableColumn description;
    public TableColumn start;
    public TableColumn end;
    public TableColumn customerId;
    public TableView contactSchedulesTable;
    public ComboBox contactCombo;

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
     * The event handler method for setting the contactSchedulesTable via the Contact combo box.
     * The table items are set via selectAppointmentByContact().
     * @param actionEvent
     * @throws SQLException
     */
    public void onContactComboSelection(ActionEvent actionEvent) throws SQLException {
        int contactId = ((Contact) contactCombo.getSelectionModel().getSelectedItem()).getContactId();

        contactSchedulesTable.setItems(selectAppointmentByContact(contactId));

        appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));

    }

    /**
     * The first method called when the ContactSchedulesReportController is initialized.
     * This method sets the data that will populate the combo box on the form.
     * A Try/Catch block is used for any SQLExceptions.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            contactCombo.setItems(helper.ContactQuery.selectAllContacts());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
