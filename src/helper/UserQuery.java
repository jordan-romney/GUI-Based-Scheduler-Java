package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**This class creates methods for querying the users table in the database.  */
public abstract class UserQuery {
    private static ObservableList<User> allUsers = FXCollections.observableArrayList();

    /**
     * The method for querying the users table in the database.
     * The method selects all users from the table and creates User objects.
     * @return ObservableList<User>
     * @throws SQLException
     */
    public static ObservableList<User> selectAllUsers() throws SQLException {
        allUsers.clear();
        String sql = "SELECT * FROM users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String password = rs.getString("Password");
            User userResult= new User(userId, userName, password);
            allUsers.add(userResult);
        }
        return allUsers;
    }

    /**
     * The method for querying the users table by username.
     * The method selects passwords by specific username and returns it in a string.
     * @param userName
     * @return The corresponding password in a string.
     * @throws SQLException
     */
    public static String validateUserNamePassword(String userName) throws SQLException {
        String sql = "SELECT Password from users WHERE User_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ResultSet rs = ps.executeQuery();
        String pw = " ";
        while (rs.next()) {
            pw = rs.getString("Password");
        }
        return pw;
        }
}
