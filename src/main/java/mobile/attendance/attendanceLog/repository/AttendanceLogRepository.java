package mobile.attendance.attendanceLog.repository;

import mobile.attendance.attendanceLog.AttendanceLog;

import java.util.List;
import java.util.Optional;

public interface AttendanceLogRepository {

    AttendanceLog save(AttendanceLog attendanceLog);

    List<AttendanceLog> findAll();

    Optional<AttendanceLog> findById(final Long id);

    int update(final AttendanceLog attendanceLog);

    int delete(final Long id);

    List<AttendanceLog> findAllByAttendanceId(Long attendanceId);

    int updateNote(AttendanceLog log);

}
