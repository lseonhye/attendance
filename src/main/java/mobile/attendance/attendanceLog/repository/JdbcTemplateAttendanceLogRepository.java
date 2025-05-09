package mobile.attendance.attendanceLog.repository;

import mobile.attendance.attendanceLog.AttendanceLog;
import mobile.attendance.user.repository.JdbcTemplateUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class JdbcTemplateAttendanceLogRepository implements AttendanceLogRepository {

    private static final Logger log = (Logger) LoggerFactory.getLogger(JdbcTemplateUserRepository.class);

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateAttendanceLogRepository(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public AttendanceLog save(final AttendanceLog attendanceLog) {
        String sql = "INSERT INTO attendance_log (user_id, attendance_id, check_in_at, check_out_at, is_present, note) VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"log_id"});
            ps.setString(1, attendanceLog.getUserId());
            ps.setLong(2, attendanceLog.getAttendanceId());
            ps.setTimestamp(3, attendanceLog.getCheckInAt());
            ps.setTimestamp(4, attendanceLog.getCheckOutAt());
            ps.setBoolean(5, attendanceLog.isPresent());
            ps.setString(6, attendanceLog.getNote());
            return ps;
        }, keyHolder);

        long key = keyHolder.getKey().longValue();

        log.debug("key: {}", key);

        attendanceLog.setLogId(key);
        return attendanceLog;
    }

    @Override
    public List<AttendanceLog> findAll() {
        String sql =  "SELECT * FROM attendance_log";
        List<AttendanceLog> attendanceList = jdbcTemplate.query(sql, rowMapper());
        log.debug("attendanceList: {}", attendanceList);
        return attendanceList;
    }

    @Override
    public Optional<AttendanceLog> findById(final Long id) {
        String sql =  "SELECT * FROM attendance_log WHERE log_id = ?";
        AttendanceLog attendanceLog = jdbcTemplate.queryForObject(sql, rowMapper(), id);
        return Optional.of(attendanceLog);
    }

    @Override
    public int update(final AttendanceLog attendanceLog) {
        String sql = "UPDATE attendance_log SET note = ? WHERE log_id = ?";
        return jdbcTemplate.update(sql, attendanceLog.getNote(), attendanceLog.getLogId());
    }

    @Override
    public int delete(final Long id) {
        String sql = "DELETE FROM attendance_log WHERE log_id = ?";
        return jdbcTemplate.update(sql, id);
    }

    private RowMapper<AttendanceLog> rowMapper() {
        return (rs, rowNum) -> new AttendanceLog(
                rs.getLong("log_id"),
                rs.getString("user_id"),
                rs.getLong("attendance_id"),
                rs.getTimestamp("check_in_at"),
                rs.getTimestamp("check_out_at"),
                rs.getBoolean("is_present"),
                rs.getString("note")
        );
    }

}
