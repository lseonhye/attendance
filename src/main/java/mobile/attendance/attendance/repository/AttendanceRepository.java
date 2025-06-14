package mobile.attendance.attendance.repository;

import mobile.attendance.attendance.Attendance;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository {

    Attendance save(Attendance attendance);

    List<Attendance> findAll();

    Optional<Attendance> findById(final Long id);

    int update(final Attendance attendance);

    int delete(final Long id);

    Optional<Attendance> findByDate(LocalDate date); // 날짜로 조회 (중복 방지)
}
