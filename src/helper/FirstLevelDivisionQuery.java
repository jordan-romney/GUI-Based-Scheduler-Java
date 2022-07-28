package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**This class creates methods for querying the first_level_divisions table in the database.  */
public abstract class FirstLevelDivisionQuery {
    private static ObservableList<FirstLevelDivision> allFirstLevelDivisions = FXCollections.observableArrayList();
    private static ObservableList<FirstLevelDivision> fldByCountryList = FXCollections.observableArrayList();

    /**
     * The method for clearing fldByCountryList.
     *  fldByCountryList is cleared.
     */
    public static void clearFLDByCountry() {
        fldByCountryList.clear();
    }

    /**
     * The method for querying the first-level-divisions table in the database.
     * The method selects all divisions from the table and creates First-Level-Division objects.
     * @return ObservableList<FirstLevelDivision>
     * @throws SQLException
     */
    public static ObservableList<FirstLevelDivision> selectAllFLD() throws SQLException {
        allFirstLevelDivisions.clear();
        String sql = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int divisionId = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            int countryId = rs.getInt("Country_ID");
            FirstLevelDivision fldResult= new FirstLevelDivision(divisionId,division, countryId);
            allFirstLevelDivisions.add(fldResult);
        }
        return allFirstLevelDivisions;
    }

    /**
     * The method for querying the first-level-divisions table in the database by country ID.
     * The method selects all divisions from the table with a specific country ID and populates and observable list.
     * @param countryId
     * @return ObservableList<FirstLevelDivision>
     * @throws SQLException
     */
    public static ObservableList<FirstLevelDivision> setFldByCountry(int countryId) throws SQLException {
        String sql = "SELECT * from  first_level_divisions WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, countryId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int divisionId = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            int cId = rs.getInt("Country_ID");
            FirstLevelDivision fldResult= new FirstLevelDivision(divisionId,division, cId);
            fldByCountryList.add(fldResult);
        }
        return fldByCountryList;
    }

    /**
     * The method for querying the first-level-divisions table in the database by division ID.
     * The method selects specific division from the table and returns it in a string.
     * @param divId
     * @return The string of the selected First-Level-Division object.
     * @throws SQLException
     */
    public static String selectFLDByDivisionId(int divId) throws SQLException {
        String div = "";
        ObservableList<FirstLevelDivision> fldByDivisionList = FXCollections.observableArrayList();
        String sql = "SELECT * from  first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int divisionId = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            int cId = rs.getInt("Country_ID");
            FirstLevelDivision fldResult = new FirstLevelDivision(divisionId, division, cId);
            fldByDivisionList.add(fldResult);
        }
        if(fldByDivisionList.size() > 0) {
            div = fldByDivisionList.get(0).toString();
        }
        return div;
    }
}
