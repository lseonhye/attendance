package mobile.attendance.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwt;

    public JwtAuthFilter(JwtTokenProvider jwt) {
        this.jwt = jwt;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws ServletException, IOException {

        String auth = req.getHeader("Authorization");
        String path = req.getRequestURI();

        // 로그인(API /api/auth/**)은 토큰 체크 없이 통과
        if (path.startsWith("/api/auth")) {
            chain.doFilter(req, res);
            return;
        }

        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);

            if (jwt.validate(token)) {
                // 🔥 기존 코드 삭제하고 아래 한 줄로 교체
                UsernamePasswordAuthenticationToken authentication =
                        jwt.getAuthentication(token);   // ROLE_STUDENT 포함

                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(req));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(req, res);
    }
}
