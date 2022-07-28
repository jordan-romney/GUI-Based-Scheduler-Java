package model;
/**This class creates members and methods for Contact objects. */
public class Contact {
    private int ContactId;
    private String contactName;
    private String email;

    /**
     * The Contact object constructor
     * @param ContactId
     * @param contactName
     * @param email
     */
    public Contact(int ContactId, String contactName, String email) {
        this.ContactId = ContactId;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * toString override.
     * @return A string display of contact objects
     */
    @Override
    public String toString() {
        return(ContactId + " " + contactName);
    }

    /**
     * @return the contact ID
     */
    public int getContactId() {
        return ContactId;
    }

    /**
     * @param contactId the contact ID to set
     */
    public void setContactId(int contactId) {
        this.ContactId = contactId;
    }

    /**
     * @return the contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * @param contactName the name to set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
