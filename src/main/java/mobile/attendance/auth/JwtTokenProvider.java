package mobile.attendance.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import mobile.attendance.user.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;


public class JwtTokenProvider {

    /* ───── 설정 값 ───── */
    private final String secret = "my-super-secret-key-at-least-32bytes!!";
    private final long   validityMs = 1000 * 60 * 60;      // 1시간
    private final Key    key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

    /* ───── 토큰 생성 ───── */
    public String createToken(User user) {                 // mobile.attendance.user.User
        return Jwts.builder()
                .setSubject(user.getUserId())
                .claim("role", user.getUserRank().name())  // "ADMIN" / "STUDENT"
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + validityMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /* ───── 토큰 유효성 검사 ───── */
    public boolean validate(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /* ───── 토큰 → userId 추출 ───── */
    public String getUserId(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    /* ───── ★ 토큰 → Authentication 변환 ───── */
    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String userId = claims.getSubject();                     // ex) "kdus"
        String role   = claims.get("role", String.class);        // ex) "STUDENT"

        // Spring Security 권한 형식은 "ROLE_XXXX"
        List<GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority("ROLE_" + role));   // ex) ROLE_STUDENT

        // 패스워드는 검증 완료 후 필요 없으므로 빈 문자열
        UserDetails principal =
                new org.springframework.security.core.userdetails.User(userId, "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
}