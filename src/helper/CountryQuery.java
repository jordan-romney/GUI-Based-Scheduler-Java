package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**This class creates methods for querying the countries table in the database.  */
public abstract class CountryQuery {
    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();
    /**
     * The method for clearing allCountries List.
     *  allCountries List is cleared.
     */
    public static void clearCountries() {
        allCountries.clear();
    }
    /**
     * The method for querying the countries table in the database.
     * The method selects all countries from the table and creates Country objects.
     * @return ObservableList<Country>
     * @throws SQLException
     */
    public static ObservableList<Country> selectAllCountries() throws SQLException {
        allCountries.clear();
        allCountries.clear();
        String sql = "SELECT * FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int countryId = rs.getInt("Country_ID");
            String country = rs.getString("Country");
            Country countryResult= new Country(countryId, country);
            allCountries.add(countryResult);
        }
        return allCountries;
    }
}
