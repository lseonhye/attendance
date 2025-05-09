package mobile.attendance.user;

public class UserSearchCondition {
    private String userId;
    private int userNumber;
    private String userPassword;
    private String userName;
    private String userRank;

    public UserSearchCondition() {}

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public int getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(final int userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(final String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getUserRank() {
        return userRank;
    }

    public void setUserRank(final String userRank) {
        this.userRank = userRank;
    }
}
