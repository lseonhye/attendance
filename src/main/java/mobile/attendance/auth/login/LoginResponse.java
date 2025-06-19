package mobile.attendance.auth.login;

public record LoginResponse(
        String token,
        String userRank,
        String userId
) {}