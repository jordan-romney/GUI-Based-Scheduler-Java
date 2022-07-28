package model;

import helper.JDBC;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

/**
 * This is the Main class of the application.
 * Everything starts here.
 */
public class Main extends Application {
    /**
     * The start method for the application.
     * LoginScreen.fxml is loaded, creates a stage and scene, then displays the scene on the stage.
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        stage.setTitle("Log In");
        stage.setScene(new Scene(root, 1400,850));
        stage.show();
    }

    /**
     * The method for loading the HomeScreen.
     * HomeScreen.fxml is loaded, creates a stage and scene, then displays the scene on the stage.
     * @param actionEvent
     * @throws IOException
     */
    public static void homeScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/view/HomeScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The entry point of the application.
     * The database connection is opened at the start of the application and closed at the end of the application.
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }
}
