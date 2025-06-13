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

    int updateCheckoutTime(AttendanceLog log); // 체크아웃 시간만 수정할 수 있는 전용 메서드

}
