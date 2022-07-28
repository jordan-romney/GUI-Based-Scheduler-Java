package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Appointment;
import model.Country;
import model.FirstLevelDivision;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
/**This class creates helper functions used in multiple classes throughout the application.  */
public abstract class HelperFunctions {
    public static Alert error = new Alert(Alert.AlertType.ERROR);
    private static String errorTitle;
    private static String errorContent;
    public static Alert inform = new Alert(Alert.AlertType.INFORMATION);
    private static String informTitle;
    private static String informContent;
    public static ZoneId easternTime = ZoneId.of("US/Eastern");
    public static ZoneId utc = ZoneId.of("UTC");
    private static String activeUser;
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy @ HH:mm");
    private static ObservableList<FirstLevelDivision> allFLD = FXCollections.observableArrayList();


    public static String getInformTitle() {
        return informTitle;
    }

    public static void setInformTitle(String infoTitle) {
        informTitle = infoTitle;
    }

    public static String getInformContent() {
        return informContent;
    }

    public static void setInformContent(String infoContent) {
        informContent = infoContent;
    }

    public static String getErrorTitle() {
        return errorTitle;
    }

    public static void setErrorTitle(String title) {
        errorTitle = title;
    }

    public static String getErrorContent() {
        return errorContent;
    }

    public static void setErrorContent(String content) {
        errorContent = content;
    }

    public static String getActiveUser() {
        return activeUser;
    }

    public static void setActiveUser(String user) {
        activeUser = user;
    }

    /**
     * The method converts a LocalDateTime to the eastern time zone.
     * A LocalDateTime and its current time zone are passed in and converted to eastern time.
     * @param time
     * @param fromZone
     * @return A LocalDateTime in eastern time.
     */
    public static LocalDateTime toEasternDateTime(LocalDateTime time, ZoneId fromZone){
        LocalDateTime et = time.atZone(fromZone).withZoneSameInstant(easternTime).toLocalDateTime();
        return et;
    }

    /**
     * The method converts a LocalDateTime to the UTC time zone.
     * A LocalDateTime and its current time zone are passed in and converted to UTC time.
     * @param time
     * @param fromZone
     * @return A LocalDateTime in UTC time.
     */
    public static LocalDateTime toUTC(LocalDateTime time, ZoneId fromZone){
        LocalDateTime UTC = time.atZone(fromZone).withZoneSameInstant(utc).toLocalDateTime();
        return UTC;
    }

    /**
     * The method converts a LocalDateTime to the machine's local time zone.
     * A LocalDateTime and its current time zone are passed in and converted to the machine's local time zone.
     * @param time
     * @param fromZone
     * @return A LocalDateTime in the machine's local time.
     */
    public static LocalDateTime toLocal(LocalDateTime time, ZoneId fromZone){
        LocalDateTime lt = time.atZone(fromZone).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
        return lt;
    }

    /**
     * The method sets the fields in an error dialogue box.
     * Strings are passed in to set the title and content of an error dialogue box.
     * The Header is set to null.
     * @param title
     * @param content
     */
    public static void errorDialogue(String title, String content){
        error.setTitle(title);
        error.setHeaderText(null);
        error.setContentText(content);
    }

    /**
     * The method sets the fields in an information dialogue box.
     * Strings are passed in to set the title and content of an information dialogue box.
     * The Header is set to null.
     * @param title
     * @param content
     */
    public static void informDialogue(String title, String content) {
        inform.setTitle(title);
        inform.setHeaderText(null);
        inform.setContentText(content);
    }

    /**
     * The method checks if the user has any appointments starting within 15 minutes of log in.
     * Various logic is used to check the start date and time of all appointments against the machines local time.
     * If any stat time is within 15 minutes of log in an information dialogue box is displayed with the appointment information.
     * @throws SQLException
     */
    public static void upcomingAppointmentCheck() throws SQLException {
        ObservableList<Appointment> allAppts = helper.AppointmentQuery.getAllAppointments();
        LocalDateTime start = null;
        int count = 0;
        for (int i = 0; i < allAppts.size(); i++) {
            start = LocalDateTime.parse(allAppts.get(i).getStart(),formatter);
            if (start.isAfter(LocalDateTime.now()) && start.isBefore(LocalDateTime.now().plusMinutes(15))) {
                informDialogue("Upcoming Appointment", "Appointment: " + allAppts.get(i).getAppointmentId() + "\n" + " Will be Staring at: " + allAppts.get(i).getStart());
                count++;
            }
        }
        if (count < 1) {
            informDialogue("No Upcoming Appointments", "You have no Upcoming Appointments");
        }
        HelperFunctions.inform.showAndWait();
    }

    /**
     * The method for finding a First-Level-Division by its ID.
     * allFLD is iterated through to compare the passed division ID and return the appropriate division.
     * @param id
     * @return The First-Level-Division object corresponding to the passed in ID
     */
    public static FirstLevelDivision findFLDById(int id) throws SQLException {
        allFLD = helper.FirstLevelDivisionQuery.selectAllFLD();
        FirstLevelDivision fld = null;
        for (int i = 0; i< allFLD.size(); i++) {
            if(allFLD.get(i).getDivisionId() == id){
                fld = allFLD.get(i);
            }
        }
        return fld;
    }

    /**
     * The method for finding a Country by its ID.
     * allFLD is iterated through to compare the passed division ID and return the appropriate division.
     * selectAllCountries() is called to populate an observable list that is iterated through to compare and return the appropriate Country.
     * @param id
     * @return The Country object corresponding to the passed in ID.
     */
    public static Country findCountryById(int id) throws SQLException {
        FirstLevelDivision fld = findFLDById(id);
        int cid = fld.getCountryId();
        Country x = null;
        ObservableList<Country> country = helper.CountryQuery.selectAllCountries();
        for (int i = 0; i< country.size(); i++) {
            if(country.get(i).getCountryId() == cid){
                x = country.get(i);
            }
        }
        return x;
    }
}
