package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**This class creates methods for querying the contacts table in the database.  */
public abstract class ContactQuery {
    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();

    /**
     * The method for querying the contacts table in the database.
     * The method selects all contacts from the table and creates Contact objects.
     * @return ObservableList<Contact>
     * @throws SQLException
     */
    public static ObservableList<Contact> selectAllContacts() throws SQLException {
        allContacts.clear();
        String sql = "SELECT * FROM contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String email = rs.getString("Email");
            Contact contactResult= new Contact(contactId, contactName, email);
            allContacts.add(contactResult);
        }
        return allContacts;
    }
}
