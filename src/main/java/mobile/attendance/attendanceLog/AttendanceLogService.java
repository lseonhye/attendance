package mobile.attendance.attendanceLog;

import mobile.attendance.attendanceLog.repository.AttendanceLogRepository;

import java.util.List;
import java.util.Optional;

public class AttendanceLogService {

    private final AttendanceLogRepository attendanceLogRepository;

    public AttendanceLogService(AttendanceLogRepository attendanceLogRepository)
    {
        this.attendanceLogRepository=attendanceLogRepository;
    }

    public AttendanceLog createAttendanceLog(final AttendanceLog attendance) {
        return attendanceLogRepository.save(attendance);
    }

    public List<AttendanceLog> findAllAttendanceLogs() {
        return attendanceLogRepository.findAll();
    }

    public Optional<AttendanceLog> findById(final Long id) {
        return attendanceLogRepository.findById(id);
    }

    public int updateAttendanceLog(final AttendanceLog attendanceLog) {
        return attendanceLogRepository.update(attendanceLog);
    }

    public int removeAttendanceLog(final Long id) {
        return attendanceLogRepository.delete(id);
    }
}
