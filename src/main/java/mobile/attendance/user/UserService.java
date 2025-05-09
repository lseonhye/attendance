package mobile.attendance.user;

import mobile.attendance.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(final User user) {
        return userRepository.save(user);
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(final String id) {
        return userRepository.findById(id);
    }

    public int updateUser(final User user) {
        return userRepository.update(user);
    }

    public int removeUser(final String id) {
        return userRepository.delete(id);
    }

    public List<User> findCustomers(final UserSearchCondition condition) {
        return userRepository.findUsers(condition);
    }
}
