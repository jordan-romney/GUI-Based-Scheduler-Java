package model;

/**This class creates members and methods for AppointmentType objects. */
public class AppointmentType {
    private String type;

    /**
     * THe AppointmentType constructor.
     * @param type
     */
    public AppointmentType(String type) {
        this.type = type;
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
     * toString override.
     * @return A string display of a AppointmentType objects
     */
    @Override
    public String toString() {
        return(type);
    }
}
