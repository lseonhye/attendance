package mobile.attendance.attendanceLog.repository;

import mobile.attendance.attendanceLog.AttendanceLog;
import mobile.attendance.attendanceLog.AttendanceLogSearchCondition;
import mobile.attendance.user.repository.JdbcTemplateUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.ArrayList;
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
        String sql = "INSERT INTO attendance_log (user_id, attendance_id, is_present, check_in_at, check_out_at, note) VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"log_id"});

            ps.setString(1, attendanceLog.getUserId());
            ps.setLong(2, attendanceLog.getAttendanceId());
            ps.setBoolean(3, attendanceLog.isPresent());

            if (attendanceLog.getCheckInAt() != null) {
                ps.setTimestamp(4, attendanceLog.getCheckInAt());
            } else {
                ps.setNull(4, java.sql.Types.TIMESTAMP);
            }

            if (attendanceLog.getCheckOutAt() != null) {
                ps.setTimestamp(5, attendanceLog.getCheckOutAt());
            } else {
                ps.setNull(5, java.sql.Types.TIMESTAMP);
            }

            if (attendanceLog.getNote() != null) {
                ps.setString(6, attendanceLog.getNote());
            } else {
                ps.setNull(6, java.sql.Types.VARCHAR);
            }
            return ps;
        }, keyHolder);

        long key = keyHolder.getKey().longValue();
        log.debug("Generated log_id: {}", key);

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
    public List<AttendanceLog> findAttendanceLogs(final AttendanceLogSearchCondition condition) {
        System.out.println(condition);
        String sql = "SELECT * FROM attendance_log WHERE 1 = 1";

        List<Object> params = new ArrayList<>();

        if (condition.getUserId() != null) {
            sql += " AND user_id = ?";
            params.add("%" + condition.getAttendanceId() + "%");
        }

        if (condition.getAttendanceId() != 0) {
            sql += " AND attendance_id = ?";
            params.add("%" + condition.getAttendanceId() + "%");
        }

        if (condition.getCheckInAt() != null) {
            sql += " AND check_in_at LIKE ?";
            params.add("%" + condition.getCheckInAt() + "%");
        }

        if (condition.getCheckOutAt() != null) {
            sql += " AND check_out_at LIKE ?";
            params.add("%" + condition.getCheckOutAt() + "%");
        }

        if (condition.getNote() != null) {
            sql += " AND note LIKE ?";
            params.add("%" + condition.getNote() + "%");
        }

        System.out.println(sql);

        return jdbcTemplate.query(sql, rowMapper(), params.toArray());
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

    @Override
    public List<AttendanceLog> findAllByAttendanceId(final Long attendanceId) {
        String sql = "SELECT * FROM attendance_log WHERE attendance_id = ?";
        return jdbcTemplate.query(sql, rowMapper(), attendanceId);
    }

    @Override
    public int updateNote(final AttendanceLog log) {
        String sql = "UPDATE attendance_log SET note = ? WHERE log_id = ?";
        return jdbcTemplate.update(sql, log.getNote(), log.getLogId());
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

    @Override
    public int updateCheckoutTime(final AttendanceLog log) {
        String sql = "UPDATE attendance_log SET check_out_at = ? WHERE log_id = ?";
        return jdbcTemplate.update(sql, log.getCheckOutAt(), log.getLogId());
    }

}
