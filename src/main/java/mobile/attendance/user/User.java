package mobile.attendance.user;

public class User {

    private String userId;
    private int userNumber;
    private String userPassword;
    private String userName;
    private UserRole userRank;

    public User() {}

    public User(String userId, int userNumber, String password, String userName, UserRole rank) {
        this.userId = userId;
        this.userNumber = userNumber;
        this.userPassword = password;
        this.userName = userName;
        this.userRank = rank;
    }

    public String getUserId() {
        return userId;
    }

    public int getUserNumber() {
        return userNumber;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public UserRole getUserRank() {
        return userRank;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public void setUserPassword(final String userPassword) {
        this.userPassword = userPassword;
    }
}
