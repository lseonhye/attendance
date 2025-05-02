package mobile.attendance.user;

public class User {

    private String userId;
    private int userNumber;
    private String userPassword;
    private String userName;
    private String userRank;

    public User(String userId, int userNumber, String password, String userName, String rank) {
        this.userId = userId;
        this.userNumber = userNumber;
        this.userPassword = password;
        this.userName = userName;
        this.userRank = rank;
    }

    public User() {}

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

    public String getUserRank() {
        return userRank;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public void setUserNumber(final int userNumber) {
        this.userNumber = userNumber;
    }

    public void setUserPassword(final String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public void setUserRank(final String userRank) {
        this.userRank = userRank;
    }
}
