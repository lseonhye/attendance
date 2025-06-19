package mobile.attendance.user.repository;

import mobile.attendance.user.User;
import mobile.attendance.user.UserRole;
import mobile.attendance.user.UserSearchCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcTemplateUserRepository implements UserRepository {

    private static final Logger log = (Logger) LoggerFactory.getLogger(JdbcTemplateUserRepository.class);

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateUserRepository(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User save(final User user) {
        String sql = "INSERT INTO users (user_id, user_number, user_password, user_name, user_rank) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getUserId());
            ps.setInt(2, user.getUserNumber());
            ps.setString(3, user.getUserPassword());
            ps.setString(4, user.getUserName());
            ps.setString(5, user.getUserRank().code());
            return ps;
        });
        return user;
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        List<User> userList = jdbcTemplate.query(sql, rowMapper());
        log.debug("userList: {}", userList);
        return userList;
    }

    @Override
    public Optional<User> findById(final String id) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, rowMapper(), id);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByNumber(int userNumber) {
        String sql = "SELECT * FROM users WHERE user_number = ?";
        List<User> result = jdbcTemplate.query(sql, rowMapper(), userNumber);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public List<User> findUsers(final UserSearchCondition condition) {
        System.out.println(condition);
        String sql = "SELECT * FROM Users WHERE 1 = 1";

        List<Object> params = new ArrayList<>();

        if (condition.getUserId() != null) {
            sql += " AND user_id LIKE ?";
            params.add("%" + condition.getUserId() + "%");
        }

        if (condition.getUserNumber() != 0) {
            sql += " AND user_number LIKE ?";
            params.add("%" + condition.getUserNumber() + "%");
        }

        if (condition.getUserName() != null) {
            sql += " AND user_name LIKE ?";
            params.add("%" + condition.getUserName() + "%");
        }

        if (condition.getUserRank() != null) {
            sql += " AND user_rank LIKE ?";
            params.add("%" + condition.getUserRank() + "%");
        }


        System.out.println(sql);

        return jdbcTemplate.query(sql, rowMapper(), params.toArray());
    }

    @Override
    public int update(final User user) {
        String sql = "UPDATE users SET user_password = ?, user_rank = ?  WHERE user_id = ?";
        return jdbcTemplate.update(sql, user.getUserPassword(), user.getUserRank(), user.getUserId());
    }

    @Override
    public int delete(final String id) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        return jdbcTemplate.update(sql, id);
    }

    private RowMapper<User> rowMapper() {
        return (rs, rowNum) -> new User(
                rs.getString("user_id"),
                rs.getInt("user_number"),
                rs.getString("user_password"),
                rs.getString("user_name"),
                UserRole.valueOf(rs.getString("user_rank").toUpperCase())
        );
    }
}
