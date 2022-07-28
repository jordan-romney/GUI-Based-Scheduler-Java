package model;

import java.time.LocalDateTime;
/**This class creates members and methods for FirstLevelDivision objects.
 * These objects are read only.
 */
public class FirstLevelDivision {
    private int divisionId;
    private String division;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String  lastUpdatedBy;
    private int countryId;

    /**
     * The FirstLevelDivision object constructor
     * @param divisionId
     * @param division
     * @param countryId
     */
    public FirstLevelDivision(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }

    /**
     * toString override.
     * @return A string display of FirstLevelDivision objects.
     */
    @Override
    public String toString() {
        return(divisionId + " " + division);
    }

    /**
     * @return the division ID
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * @return the division name
     */
    public String getDivision() {
        return division;
    }

    /**
     * @return the country ID
     */
    public int getCountryId() {
        return countryId;
    }

}
