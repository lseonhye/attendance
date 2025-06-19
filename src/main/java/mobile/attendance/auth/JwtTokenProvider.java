package mobile.attendance.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import mobile.attendance.user.User;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


public class JwtTokenProvider {
    private final String secret = "my-super-secret-key-at-least-32bytes!!";
    private final long validityMs = 1000 * 60 * 60; // 1시간
    private final Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

    public String createToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUserId())
                .claim("role", user.getUserRank().name())   // 'ADMIN' / 'STUDENT'
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + validityMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validate(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserId(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
