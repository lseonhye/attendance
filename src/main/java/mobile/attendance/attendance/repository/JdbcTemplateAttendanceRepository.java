package mobile.attendance.attendance.repository;

import mobile.attendance.attendance.Attendance;
import mobile.attendance.user.repository.JdbcTemplateUserRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class JdbcTemplateAttendanceRepository implements AttendanceRepository {

    private static final Logger log = (Logger) LoggerFactory.getLogger(JdbcTemplateUserRepository.class);

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateAttendanceRepository(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    @Transactional
    public Attendance save(final Attendance attendance) {
        String checkSql = "SELECT COUNT(*) FROM attendance WHERE attendance_date = ?";
        int count = jdbcTemplate.queryForObject(checkSql, new Object[]{java.sql.Date.valueOf(attendance.getAttendanceDate())}, Integer.class);

        if (count > 0) {
            throw new IllegalArgumentException("Attendance date already exists.");
        }
        String sql =  "INSERT INTO attendance (attendance_date, memo) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        final LocalDate finalAttendanceDate = attendance.getAttendanceDate();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"attendance_id"});
            ps.setDate(1, Date.valueOf(finalAttendanceDate));
            ps.setString(2, attendance.getMemo());
            return ps;
        }, keyHolder);

        long key = keyHolder.getKey().longValue();
        log.debug("Generated key: {}", key);

        attendance.setAttendanceId(key);
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

    @Override
    public Optional<Attendance> findByDate(final LocalDate date) {
        String sql = "SELECT * FROM attendance WHERE attendance_date = ?";

        List<Attendance> results = jdbcTemplate.query(
                sql,
                new Object[]{Date.valueOf(date)},
                new BeanPropertyRowMapper<>(Attendance.class)
        );

        if (results.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(results.get(0));
    }

    private RowMapper<Attendance> rowMapper() {
        return (rs, rowNum) -> new Attendance(
                rs.getLong("attendance_id"),
                rs.getDate("attendance_date").toLocalDate(),
                rs.getString("memo")
        );
    }
}
