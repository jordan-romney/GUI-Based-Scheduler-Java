package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.AppointmentType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import static helper.HelperFunctions.*;
/**This class creates methods for querying the appointments table in the database.  */
public abstract class AppointmentQuery {
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    public static ObservableList<Appointment> getAllAppointments(){
        return allAppointments;
    }

    public static void removeAppointmentByObj(Appointment appt) {
        allAppointments.remove(appt);
    }

    public static void removeAppointmentByIndex(int index){
        allAppointments.remove(index);
    }

    /**
     * The method for inserting an appointment into the appointments table in the database.
     * The method inserts appointment objects into the appointments table.
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerId
     * @param userId
     * @param contactId
     * @return The number of rows affected by the insert.
     * @throws SQLException
     */
    public static int insert (String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) throws SQLException {
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Timestamp startDate = Timestamp.valueOf(toUTC(start, ZoneId.systemDefault()));
        Timestamp endDate = Timestamp.valueOf(toUTC(end, ZoneId.systemDefault()));
        Timestamp createDate = Timestamp.valueOf(toUTC(LocalDateTime.now(), ZoneId.systemDefault()));
        Timestamp lastUpdate = Timestamp.valueOf(toUTC(LocalDateTime.now(), ZoneId.systemDefault()));
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, startDate);
        ps.setTimestamp(6, endDate);
        ps.setTimestamp(7, createDate);
        ps.setString(8, HelperFunctions.getActiveUser());
        ps.setTimestamp(9, lastUpdate);
        ps.setString(10, HelperFunctions.getActiveUser());
        ps.setInt(11, customerId);
        ps.setInt(12, userId);
        ps.setInt(13, contactId);

        int rowsAffected = ps.executeUpdate();
        if(rowsAffected >0){
            System.out.println("Insert Successful!");
        }
        else{
            System.out.println("Insert Unsuccessful!");
        }
        return rowsAffected;
    }

    /**
     * The method for updating an appointment in the appointments table of the database.
     * The method updates a specific appointment object by appointment ID in the appointments table.
     * @param appointmentId
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerId
     * @param userId
     * @param contactId
     * @return The number of rows affected by the update.
     * @throws SQLException
     */
    public static int update (int appointmentId, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        Timestamp startDate = Timestamp.valueOf(toUTC(start, ZoneId.systemDefault()));
        Timestamp endDate = Timestamp.valueOf(toUTC(end, ZoneId.systemDefault()));
        Timestamp lastUpdate = Timestamp.valueOf(toUTC(LocalDateTime.now(), ZoneId.systemDefault()));
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, startDate);
        ps.setTimestamp(6, endDate);
        ps.setTimestamp(7, lastUpdate);
        ps.setString(8, HelperFunctions.getActiveUser());
        ps.setInt(9, customerId);
        ps.setInt(10, userId);
        ps.setInt(11, contactId);
        ps.setInt(12, appointmentId);


        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Update Successful!");
        } else {
            System.out.println("Update Unsuccessful!");
        }
        return rowsAffected;
    }

    /**
     * The method for deleting an appointment from the appointments table in the database.
     * The method deletes a specific appointment object by appointment ID from the appointments table.
     * @param appointmentId
     * @return The number of rows affected by the delete.
     * @throws SQLException
     */
    public static int delete(int appointmentId) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);

        int rowsAffected = ps.executeUpdate();
        if(rowsAffected >0){
            System.out.println("Delete Successful!");
        }
        else{
            System.out.println("Delete Unsuccessful!");
        }
        return rowsAffected;
    }

    /**
     * The method for querying the appointments table in the database.
     * The method selects all appointments from the table and creates Appointment objects.
     * @return ObservableList<Appointment>
     * @throws SQLException
     */
    public static ObservableList<Appointment> selectAllAppointmentAdd() throws SQLException {
        allAppointments.clear();
        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = toLocal((rs.getTimestamp("Start")).toLocalDateTime(), utc);
            LocalDateTime end = toLocal((rs.getTimestamp("End")).toLocalDateTime(), utc);
            LocalDateTime createDate = toLocal((rs.getTimestamp("Create_Date")).toLocalDateTime(), utc);
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = toLocal((rs.getTimestamp("Last_Update")).toLocalDateTime(), utc);
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            Appointment appointmentResult= new Appointment(appointmentId, title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy,customerId, userId, contactId);
            allAppointments.add(appointmentResult);
        }
        return allAppointments;
    }

    /**
     * The method for querying the appointment table in the database by start date and time.
     * The method populates an observable list with specific appointments selected by their start date and time.
     * @param sDT
     * @throws SQLException
     */
    public static void selectAppointmentByStartAdd(LocalDateTime sDT) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE Start = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setTimestamp(1, Timestamp.valueOf(sDT));
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = toLocal((rs.getTimestamp("Start")).toLocalDateTime(), utc);
            LocalDateTime end = toLocal((rs.getTimestamp("End")).toLocalDateTime(), utc);
            LocalDateTime createDate = toLocal((rs.getTimestamp("Create_Date")).toLocalDateTime(), utc);
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = toLocal((rs.getTimestamp("Last_Update")).toLocalDateTime(), utc);
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            Appointment appointmentResult= new Appointment(appointmentId, title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy,customerId, userId, contactId);
            allAppointments.add(appointmentResult);
        }
    }

    /**
     * The method for querying the appointment table in the database by customer ID.
     * The method populates an observable list with specific appointments selected by customer ID.
     * @param custId
     * @return ObservableList<Appointment>
     * @throws SQLException
     */
    public static ObservableList<Appointment> selectAppointmentByCustomer(int custId) throws SQLException {
        ObservableList<Appointment> appointmentsByCustomer = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, custId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = toLocal((rs.getTimestamp("Start")).toLocalDateTime(), utc);
            LocalDateTime end = toLocal((rs.getTimestamp("End")).toLocalDateTime(), utc);
            LocalDateTime createDate = toLocal((rs.getTimestamp("Create_Date")).toLocalDateTime(), utc);
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = toLocal((rs.getTimestamp("Last_Update")).toLocalDateTime(), utc);
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            Appointment appointmentResult = new Appointment(appointmentId, title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);
            appointmentsByCustomer.add(appointmentResult);
        }
        return appointmentsByCustomer;
    }

    /**
     * The method for querying the appointment table in the database by contact ID.
     * The method populates an observable list with specific appointments selected by contact ID.
     * @param contact
     * @return ObservableList<Appointment>
     * @throws SQLException
     */
    public static ObservableList<Appointment> selectAppointmentByContact(int contact) throws SQLException {
        ObservableList<Appointment> appointmentsByContact = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contact);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = toLocal((rs.getTimestamp("Start")).toLocalDateTime(), utc);
            LocalDateTime end = toLocal((rs.getTimestamp("End")).toLocalDateTime(), utc);
            LocalDateTime createDate = toLocal((rs.getTimestamp("Create_Date")).toLocalDateTime(), utc);
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = toLocal((rs.getTimestamp("Last_Update")).toLocalDateTime(), utc);
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            Appointment appointmentResult = new Appointment(appointmentId, title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);
            appointmentsByContact.add(appointmentResult);
        }
        return appointmentsByContact;
    }

    /**
     * The method for querying the appointment table in the database by distinct type.
     * The method creates type objects for the distinct types present in the table.
     * @return ObservableList<appointmentType>
     * @throws SQLException
     */
    public static ObservableList<AppointmentType> selectAppointmentTypes() throws SQLException {
        ObservableList<AppointmentType> types = FXCollections.observableArrayList();
        String sql = "SELECT DISTINCT Type FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String type = rs.getString("Type");
            AppointmentType appointmentTypeResult = new AppointmentType(type);
            types.add(appointmentTypeResult);
        }
        return types;
    }

    /**
     * The method for querying the appointment table in the database by stating month.
     * The method counts the number of appointments in a specific month.
     * @param month
     * @return A total count of appointment by specific month
     * @throws SQLException
     */
    public static int selectAppointmentByMonth(int month) throws SQLException {
        int listCount = 0;
        ObservableList<Appointment> appointmentsByMonth = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE MONTH(Start) = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, month);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            listCount++;
        }
        return listCount;
    }

    /**
     * The method for querying the appointment table in the database by type.
     * The method counts the number of appointments of a specific type.
     * @param apptType
     * @return A total count of appointment by specific type.
     * @throws SQLException
     */
    public static int selectAppointmentByType(String apptType) throws SQLException {
        int listCount = 0;
        ObservableList<Appointment> appointmentsByType = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Type = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, apptType);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            listCount++;
        }
        return listCount;
    }

    /**
     * The method for querying the appointment table in the database by type and start month.
     * The method counts the number of appointments of a specific type in a specific month.
     * @param apptType
     * @param month
     * @return A total count of appointment by specific type in a specific month.
     * @throws SQLException
     */
    public static int selectAppointmentByTypeAndMonth(String apptType, int month) throws SQLException {
        int listCount = 0;
        ObservableList<Appointment> appointmentsByTypeAndMonth = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Type = ? AND MONTH(Start) = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, apptType);
        ps.setInt(2, month);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            listCount++;
        }
        return listCount;
    }

    /**
     * The method for querying the appointments table in the database.
     * The method counts all appointments in the table.
     * @return A total count of appointments.
     * @throws SQLException
     */
    public static int selectAllAppointment() throws SQLException {
        int listCount = 0;
        ObservableList<Appointment> allAppts = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            listCount++;
        }
        return listCount;
    }

    /**
     * The method for querying the appointments table in the database ordered by count of customer ID occurrences.
     * The method orders and sorts the table by counting the number of times a customer ID occurs.
     * @return The ID of the customer with the most appointment occurrences.
     * @throws SQLException
     */
    public static int selectTopCustomer() throws SQLException {
        int topCustId = -1;
        allAppointments.clear();
        String sql = "SELECT *, COUNT(Customer_ID) FROM appointments GROUP BY Customer_ID ORDER BY COUNT(Customer_ID) DESC";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = toLocal((rs.getTimestamp("Start")).toLocalDateTime(), utc);
            LocalDateTime end = toLocal((rs.getTimestamp("End")).toLocalDateTime(), utc);
            LocalDateTime createDate = toLocal((rs.getTimestamp("Create_Date")).toLocalDateTime(), utc);
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = toLocal((rs.getTimestamp("Last_Update")).toLocalDateTime(), utc);
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            Appointment appointmentResult = new Appointment(appointmentId, title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);
            allAppointments.add(appointmentResult);
        }
        if(allAppointments.size() > 0) {
            topCustId = allAppointments.get(0).getCustomerId();
        }
        return topCustId;
    }
}
