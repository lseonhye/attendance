package mobile.attendance.user.repository;

import mobile.attendance.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(final User user);

    List<User> findAll();

    Optional<User> findById(final String id);

    int update(final User user);

    int delete(final String id);

}
