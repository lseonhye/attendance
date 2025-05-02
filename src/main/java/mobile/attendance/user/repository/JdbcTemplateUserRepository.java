package mobile.attendance.user.repository;

import mobile.attendance.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
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
        String sql =  "INSERT INTO users (user_id, user_number, user_password, user_name, user_rank) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getUserId());
            ps.setInt(2, user.getUserNumber());
            ps.setString(3, user.getUserPassword());
            ps.setString(4, user.getUserName());
            ps.setString(5, user.getUserRank());
            return ps;
        });
        return user;
    }

    @Override
    public List<User> findAll() {
        String sql =  "SELECT * FROM users";
        List<User> userList = jdbcTemplate.query(sql, rowMapper());
        log.debug("userList: {}", userList);
        return userList;
    }

    @Override
    public Optional<User> findById(final String id) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        User user = jdbcTemplate.queryForObject(sql, rowMapper(), id);
        return Optional.of(user);
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
                rs.getString("user_rank")
        );
    }
}
