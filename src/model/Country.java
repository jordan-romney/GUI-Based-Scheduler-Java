package model;

import java.time.LocalDateTime;
/**This class creates members and methods for Country objects.
 * These objects are read only.
 */
public class Country {
    private int countryId;
    private String country;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdated;
    private String lasUpdatedBy;

    /**
     * The Country object constructor
     * @param countryId
     * @param country
     */
    public Country(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    /**
     * toString override.
     * @return A string display of  Country objects.
     */
    @Override
    public String toString() {
        return(countryId + " " + country);
    }

    /**
     * @return the country name
     */
    public String getCountry() {
        return country;
    }

    /**
     * @return the country ID
     */
    public int getCountryId() {
        return countryId;
    }

}
