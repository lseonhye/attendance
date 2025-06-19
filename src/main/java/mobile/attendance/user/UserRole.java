package mobile.attendance.user;

import java.util.Arrays;

public enum UserRole {
    ADMIN("ADMIN"),
    STUDENT("STUDENT"),
    MASTER("MASTER"),
    PROFESSOR("PROFESSOR"),
    DOCTORATE("DOCTORATE");

    private final String code;

    UserRole(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

    public static UserRole fromCode(String code) {
        return Arrays.stream(values())
                .filter(r -> r.code.equals(code))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown role: " + code));
    }
}
