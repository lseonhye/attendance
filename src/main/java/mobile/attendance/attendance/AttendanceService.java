package mobile.attendance.attendance;

import mobile.attendance.attendance.repository.AttendanceRepository;
import mobile.attendance.user.repository.JdbcTemplateUserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;

public class AttendanceService {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(JdbcTemplateUserRepository.class);

    private final AttendanceRepository attendanceRepository;

    public AttendanceService(AttendanceRepository attendanceRepository)
    {
        this.attendanceRepository=attendanceRepository;
    }

    public Attendance createAttendance(final Attendance attendance) {
        Optional<Attendance> existing = attendanceRepository.findByDate(attendance.getAttendanceDate());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("해당 날짜의 출석 정보는 이미 존재합니다.");
        }
        return attendanceRepository.save(attendance);
    }

    public List<Attendance> findAllAttendances() {
        return attendanceRepository.findAll();
    }

    public Optional<Attendance> findById(final Long id) {
        return attendanceRepository.findById(id);
    }

    public int updateAttendance(final Attendance attendance) {
        return attendanceRepository.update(attendance);
    }

    public int removeAttendance(final Long id) {
        return attendanceRepository.delete(id);
    }
}
