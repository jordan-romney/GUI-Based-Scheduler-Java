package model;

import java.time.LocalDateTime;
/**This class creates members and methods for Customer objects. */
public class Customer {
    private int CustomerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int divisionId;
    private int countryId;

    /**
     * The Customer object constructor
     * @param customerID
     * @param customerName
     * @param address
     * @param postalCode
     * @param phoneNumber
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param divisionId
     */
    public Customer (int customerID, String customerName, String address, String postalCode, String phoneNumber, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int divisionId, int countryId){
        this.CustomerId = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
        this.countryId = countryId;
    }

    /**
     * toString override.
     * @return A string display of customer objects.
     */
    @Override
    public String toString() {
        return(CustomerId + " " + customerName);
    }

    /**
     * @return the customer ID
     */
    public int getCustomerId() {
        return CustomerId;
    }

    /**
     * @param customerId the customer ID to set
     */
    public void setCustomerId(int customerId) {
        this.CustomerId = customerId;
    }

    /**
     * @return the customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customer name to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode the postal code to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phone number to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the date created
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the created date to set
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the creator
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the creator to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the date and time of the last update
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate the date and time of the last update to set
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return the updater
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * @param lastUpdatedBy the updater to set
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * @return the division ID
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * @param divisionId the division ID to set
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * @return the country ID
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * @param countryId the country ID to set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}


