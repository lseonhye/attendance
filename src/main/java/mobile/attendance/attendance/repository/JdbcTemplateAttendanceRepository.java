package mobile.attendance.attendance.repository;

import mobile.attendance.attendance.Attendance;
import mobile.attendance.user.repository.JdbcTemplateUserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class JdbcTemplateAttendanceRepository implements AttendanceRepository {

    private static final Logger log = (Logger) LoggerFactory.getLogger(JdbcTemplateUserRepository.class);

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateAttendanceRepository(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Attendance save(final Attendance attendance) {
        String sql =  "INSERT INTO attendance (attendance_id, attendance_date, memo) VALUES (?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, attendance.getAttendanceId());
            ps.setDate(2, attendance.getAttendanceDate());
            ps.setString(3, attendance.getMemo());
            return ps;
        });
        return attendance;
    }

    @Override
    public List<Attendance> findAll() {
        String sql =  "SELECT * FROM attendance";
        List<Attendance> attendanceList = jdbcTemplate.query(sql, rowMapper());
        log.debug("attendanceList: {}", attendanceList);
        return attendanceList;
    }

    @Override
    public Optional<Attendance> findById(final Long id) {
        String sql =  "SELECT * FROM attendance WHERE attendance_id = ?";
        Attendance attendance = jdbcTemplate.queryForObject(sql, rowMapper(), id);
        return Optional.of(attendance);
    }

    @Override
    public int update(final Attendance attendance) {
        String sql = "UPDATE attendance SET memo = ? WHERE attendance_id = ?";
        return jdbcTemplate.update(sql, attendance.getMemo(), attendance.getAttendanceId());
    }

    @Override
    public int delete(final Long id) {
        String sql = "DELETE FROM attendance WHERE attendance_id = ?";
        return jdbcTemplate.update(sql, id);
    }

    private RowMapper<Attendance> rowMapper() {
        return (rs, rowNum) -> new Attendance(
                rs.getLong("attendance_id"),
                rs.getDate("attendance_date"),
                rs.getString("memo")
        );
    }
}
