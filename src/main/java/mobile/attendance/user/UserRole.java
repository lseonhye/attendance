package mobile.attendance.user;

public enum UserRole {
    ADMIN("ADMIN"),
    STUDENT("STUDENT"),
    MASTER("MASTER"),
    PROFESSOR("PROFESSOR"),
    DOCTORATE("DOCTORATE");

    private final String code;

    UserRole(final String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }
}
