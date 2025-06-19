package mobile.attendance.user;

public class UserRequest {
    private String userId;
    private int userNumber;
    private String userPassword;
    private String userName;
    private UserRole userRank;

    public UserRequest() {}

    public UserRequest(final String userId, final int userNumber, final String userPassword, final String userName, final UserRole userRank) {
        this.userId = userId;
        this.userNumber = userNumber;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userRank = userRank;
    }

    public UserRequest(final int userNumber, final String password, final String userName, final UserRole userRank) {
        this.userNumber = userNumber;
        this.userPassword = password;
        this.userName = userName;
        this.userRank = userRank;
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

    public UserRole getUserRank(final UserRole userRank) {
        return userRank;
    }
}
