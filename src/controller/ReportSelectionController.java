package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import static model.Main.homeScreen;
/**This class creates a controller for ReportSelection.fxml. */
public class ReportSelectionController {
    /**
     * The event handler method for the Appointment Totals button.
     * The user is directed to the Appointment Totals report.
     * @param actionEvent
     * @throws IOException
     */
    public void onAppointmentTotalsButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentTotalsReport.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Appointment Totals");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The event handler method for the Contact Schedules button.
     * The user is directed to the Contact Schedules report.
     * @param actionEvent
     * @throws IOException
     */
    public void onContactSchedulesButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ContactSchedulesReport.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Contact Schedules");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The event handler method for the Top First-Level-Division button.
     * The user is directed to the Top First-Level-Division report.
     * @param actionEvent
     * @throws IOException
     */
    public void onTopFLDButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/TopFLDReport.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Top First-Level-Division");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The event handler method for the close button.
     * homeScreen() is called and the user is directed back to the home screen.
     * @param actionEvent
     * @throws IOException
     */
    public void onCloseButton(ActionEvent actionEvent) throws IOException {
        homeScreen(actionEvent);
    }


}
