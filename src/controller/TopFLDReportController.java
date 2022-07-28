package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import static helper.AppointmentQuery.selectTopCustomer;
import static helper.CustomerQuery.selectCustomersById;
import static helper.FirstLevelDivisionQuery.selectFLDByDivisionId;
/**This class creates a controller for TopFLDReport.fxml. */
public class TopFLDReportController implements Initializable {
    public Label fldLabel;

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
     *  The first method called when the TopFLDReportController is initialized.
     *  This method sets the label text with the top division.
     *  A Try/Catch block is used for any SQLExceptions.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            fldLabel.setText(selectFLDByDivisionId(selectCustomersById(selectTopCustomer()).getDivisionId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
