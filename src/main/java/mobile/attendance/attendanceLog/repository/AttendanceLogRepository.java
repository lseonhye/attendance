package mobile.attendance.attendanceLog.repository;

import mobile.attendance.attendanceLog.AttendanceLog;
import mobile.attendance.attendanceLog.AttendanceLogSearchCondition;

import java.util.List;
import java.util.Optional;

public interface AttendanceLogRepository {

    AttendanceLog save(AttendanceLog attendanceLog);

    List<AttendanceLog> findAll();

    Optional<AttendanceLog> findById(final Long id);

    List<AttendanceLog> findAttendanceLogs(AttendanceLogSearchCondition condition);

    int update(final AttendanceLog attendanceLog);

    int delete(final Long id);

    List<AttendanceLog> findAllByAttendanceId(Long attendanceId);

    int updateNote(AttendanceLog log);

    int updateCheckoutTime(AttendanceLog log); // 체크아웃 시간 업데이트
}
