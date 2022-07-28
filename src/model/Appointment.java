package model;

import helper.DateTimeFormat;
import helper.HelperFunctions;
import java.time.LocalDateTime;

/**This class creates members and methods for Appointment objects. */
public class Appointment {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private String start;
    private String end;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int customerId;
    private int userId;
    private int contactId;

    /**
     * The Appointment object constructor.
     * @param appointmentId
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param customerId
     * @param userId
     * @param contactId
     */
    public Appointment(int appointmentId, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = dateTimeFormat.formatter(start);
        this.end = dateTimeFormat.formatter(end);
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * A lambda expression that formats a LocalDateTime to a string.
     * A LocalDateTime is passed and formatted with HelperFunctions.formatter.
     * A string is returned.
     */
    DateTimeFormat dateTimeFormat = (LocalDateTime ldt) -> {
        String formatLDT = ldt.format(HelperFunctions.formatter);
        return formatLDT;
    };

    /**
     * @return the appointment ID
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * @param appointmentId the appointment ID to set
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the start
     */
    public String getStart() {
        return start;
    }

    /**
     * The set method for the start parameter.
     * The dateTimeFormat lambda expression is used to format start to a string.
     * @param start The start date and time to set
     */
    public void setStart(LocalDateTime start) {
        this.start = dateTimeFormat.formatter(start);
    }

    /**
     * @return the end
     */
    public String getEnd() {
        return end;
    }

    /**
     * The set method for the end parameter.
     * The dateTimeFormat lambda expression is used to format end to a string.
     * @param end The end date and time to set
     */
    public void setEnd(LocalDateTime end) {
        this.end = dateTimeFormat.formatter(end);
    }

    /**
     * @return the created date
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
     * @return the user who created the appointment
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
     * @param lastUpdate the update date and time to set
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return the user who last updated the appointment
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
     * @return the customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customer ID to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the user ID to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the contact ID
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * @param contactId the contact ID to set
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
