package mobile.attendance.auth;

import mobile.attendance.user.User;
import mobile.attendance.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);


    public AuthService(UserRepository repo, PasswordEncoder encoder) {
        this.userRepository = repo;
        this.encoder = encoder;
    }

    public User authenticate(String id, String pw) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID"));

        if (!encoder.matches(pw, user.getUserPassword())) {
            throw new IllegalArgumentException("비밀번호 불일치");
        }
        return user;
    }
}