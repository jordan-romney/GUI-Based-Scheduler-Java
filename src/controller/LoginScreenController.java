package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import helper.HelperFunctions;
/**This class creates a controller for LoginScreen.fxml.  */
public class LoginScreenController implements Initializable {
    public Label passwordLabel;
    public Button logInButton;
    public Label usernameLabel;
    public Label welcomeLabel;
    public TextField username;
    public TextField password;
    public Label locationLabel;
    public Label physicalLocationLabel;
    public Label pleaseEnterLabel;

    /**
     * The event handler method for the Log In button.
     * validateUserNamePassword() is called to validate log in.
     * Error messages are displayed if user was not validated.
     * Each log in attempt and time is written to login_activity.txt and notes if it was successful or unsuccessful.
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void onLogInButton(ActionEvent actionEvent) throws IOException, SQLException {
        FileWriter fw = new FileWriter("login_activity.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter writer = new PrintWriter(bw);
        if (helper.UserQuery.validateUserNamePassword(username.getText()).equals(password.getText())) {
            writer.println("Log In Successful\nUsername: " + username.getText() + "\nLog In Date & Time: " + HelperFunctions.toUTC(LocalDateTime.now(), ZoneId.systemDefault()) + " UTC\nLog in location: " +ZoneId.systemDefault() + "\n");
            writer.close();
            Parent root = FXMLLoader.load(getClass().getResource("/view/HomeScreen.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Home");
            stage.setScene(scene);
            stage.show();
            HelperFunctions.setActiveUser(username.getText());
            HelperFunctions.upcomingAppointmentCheck();
        }
        else{
            writer.println("Log In Unsuccessful\nUsername: " + username.getText() + "\nLog In Date & Time: " + HelperFunctions.toUTC(LocalDateTime.now(), ZoneId.systemDefault())+ "\nUTC, Log in location: " +ZoneId.systemDefault() + "\n");
            writer.close();
            HelperFunctions.error.showAndWait();
            }
        }

    /**
     * The event handler method for pressing enter on the keyboard during password entry.
     * onLogInButton() is called when enter is pressed on the keyboard during password entry
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void onPasswordEnter(ActionEvent actionEvent) throws SQLException, IOException {
        onLogInButton(actionEvent);
    }

    /**
     * The first method called when theLogInController is initialized.
     * The users location and locale are checked to determine if French or English text needs to be displayed.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Locale.setDefault(Locale.FRENCH);
        physicalLocationLabel.setText(ZoneId.systemDefault().toString());

        ResourceBundle rb = ResourceBundle.getBundle("helper/RB_fr", Locale.getDefault());

        if (Locale.getDefault().getLanguage().equals("fr")) {
            welcomeLabel.setText(rb.getString("welcome"));
            locationLabel.setText(rb.getString("location"));
            pleaseEnterLabel.setText(rb.getString("pleaseEnter"));
            usernameLabel.setText(rb.getString("username"));
            passwordLabel.setText(rb.getString("password"));
            logInButton.setText(rb.getString("login"));
            HelperFunctions.errorDialogue(rb.getString("errorTitle"), rb.getString("errorContent"));
        }
        else{
            HelperFunctions.errorDialogue("Validation Error", "Please Enter a Valid Username or Password");
        }

    }
}

