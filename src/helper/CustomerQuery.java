package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import static helper.HelperFunctions.*;
/**This class creates methods for querying the customers table in the database.  */
public abstract class CustomerQuery {
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public static void removeCustomerByObj(Customer cust) {
        allCustomers.remove(cust);
    }

    /**
     * The method for inserting a customer into the customers table in the database.
     * The method inserts customer objects into the customers table.
     * @param customerName
     * @param address
     * @param postalCode
     * @param phoneNumber
     * @param divisionId
     * @return The number of rows affected by the insert.
     * @throws SQLException
     */
    public static int insert (String customerName, String address, String postalCode, String phoneNumber, int divisionId) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Timestamp createDate = Timestamp.valueOf(toUTC(LocalDateTime.now(), ZoneId.systemDefault()));
        Timestamp lastUpdate = Timestamp.valueOf(toUTC(LocalDateTime.now(), ZoneId.systemDefault()));
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNumber);
        ps.setTimestamp(5, createDate );
        ps.setString(6, helper.HelperFunctions.getActiveUser());
        ps.setTimestamp(7, lastUpdate);
        ps.setString(8, helper.HelperFunctions.getActiveUser());
        ps.setInt(9, divisionId);

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
     * The method for updating a customer in the customers table of the database.
     * The method updates a specific customer object by customer ID in the customers table.
     * @param customerId
     * @param customerName
     * @param address
     * @param postalCode
     * @param phoneNumber
     * @param divisionId
     * @return The number of rows affected by the update.
     * @throws SQLException
     */
    public static int update (int customerId, String customerName, String address, String postalCode, String phoneNumber, int divisionId) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        Timestamp lastUpdate = Timestamp.valueOf(toUTC(LocalDateTime.now(), ZoneId.systemDefault()));
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNumber);
        ps.setTimestamp(5, lastUpdate );
        ps.setString(6, HelperFunctions.getActiveUser());
        ps.setInt(7, divisionId);
        ps.setInt(8, customerId);

        int rowsAffected = ps.executeUpdate();
        if(rowsAffected >0){
            System.out.println("Update Successful!");
        }
        else{
            System.out.println("Update Unsuccessful!");
        }
        return rowsAffected;
    }

    /**
     * The method for deleting a customer from the customers table in the database.
     * The method deletes a specific customer object by customer ID from the customers table.
     * @param customerId
     * @return The number of rows affected by the delete.
     * @throws SQLException
     */
    public static int delete(int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);

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
     * The method for querying the customers table in the database.
     * The method selects all customers from the table and creates Customer objects.
     * @return ObservableList<Customer>
     * @throws SQLException
     */
    public static ObservableList<Customer> selectAllCustomers() throws SQLException {
        allCustomers.clear();
        String sql = "SELECT * FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phoneNumber = rs.getString("Phone");
            LocalDateTime createDate = toLocal((rs.getTimestamp("Create_Date")).toLocalDateTime(), utc);
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = toLocal((rs.getTimestamp("Last_Update")).toLocalDateTime(), utc);
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int divisionId = rs.getInt("Division_ID");
            int countryId = HelperFunctions.findCountryById(divisionId).getCountryId();
            Customer customerResult= new Customer(customerId, customerName, address, postalCode, phoneNumber,createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId, countryId);
            allCustomers.add(customerResult);
        }
        return allCustomers;
    }

    /**
     * The method for querying the customers table in the database by customer name.
     * The method selects specific customers from the table and creates an observable list.
     * @param name
     * @throws SQLException
     */
    public static void selectCustomersByNameAdd(String name) throws SQLException {
        String sql = "SELECT * FROM customers WHERE Customer_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phoneNumber = rs.getString("Phone");
            LocalDateTime createDate = toLocal((rs.getTimestamp("Create_Date")).toLocalDateTime(), utc);
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = toLocal((rs.getTimestamp("Last_Update")).toLocalDateTime(), utc);
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int divisionId = rs.getInt("Division_ID");
            int countryId = HelperFunctions.findCountryById(divisionId).getCountryId();
            Customer customerResult = new Customer(customerId, customerName, address, postalCode, phoneNumber, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId, countryId);
            allCustomers.add(customerResult);
        }
    }

    /**
     * The method for querying the customers table in the database by customer ID.
     * The method selects specific customers from the table and creates a Customer Object.
     * @param id
     * @return A Customer object.
     * @throws SQLException
     */
    public static Customer selectCustomersById(int id) throws SQLException {
        Customer customerResult = null;
        ObservableList<Customer> customersById = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customers WHERE Customer_Id = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phoneNumber = rs.getString("Phone");
            LocalDateTime createDate = toLocal((rs.getTimestamp("Create_Date")).toLocalDateTime(), utc);
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = toLocal((rs.getTimestamp("Last_Update")).toLocalDateTime(), utc);
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int divisionId = rs.getInt("Division_ID");
            int countryId = HelperFunctions.findCountryById(divisionId).getCountryId();
            customerResult = new Customer(customerId, customerName, address, postalCode, phoneNumber, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId, countryId);
            customersById.add(customerResult);
        }
        return customerResult;
    }
}
