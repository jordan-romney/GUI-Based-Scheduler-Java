package model;
/**This class creates members and methods for User objects. */
public class User {
    private int UserId;
    private String userName;
    private String password;

    public User(int Id, String userName, String password) {
        this.UserId = Id;
        this.userName = userName;
        this.password = password;
    }

    /**
     * toString override
     * @return A string display of User objects.
     */
    @Override
    public String toString() {
        return(UserId + " " + userName);
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return UserId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.UserId = userId;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }



}
