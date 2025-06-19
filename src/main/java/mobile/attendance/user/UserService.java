package mobile.attendance.user;

import mobile.attendance.user.repository.UserRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserService(final UserRepository userRepository, final PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public User createUser(final User user) {
        if (userRepository.findById(user.getUserId()).isPresent()) {
            throw new DuplicateKeyException("중복 아이디");
        }
        if (userRepository.findByNumber(user.getUserNumber()).isPresent()) {
            throw new DuplicateKeyException("중복 학번");
        }
        user.setUserPassword(encoder.encode(user.getUserPassword()));
        return userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    List<User> findUsers(UserSearchCondition condition) {
        return userRepository.findUsers(condition);
    }

    public Optional<User> findUserById(final String id) {
        return userRepository.findById(id);
    }

    public void updateUser(final User user) {

        boolean changePw = user.getUserPassword() != null && !user.getUserPassword().isBlank();

        if (changePw) {
            user.setUserPassword(encoder.encode(user.getUserPassword()));
        } else {
            String currentPw = userRepository.findById(user.getUserId())
                    .map(User::getUserPassword)
                    .orElseThrow();
            user.setUserPassword(currentPw);
        }

        userRepository.update(user);
    }

    public void removeUser(final String id) {
        userRepository.delete(id);
    }
}