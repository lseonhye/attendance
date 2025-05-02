package mobile.attendance.attendance.repository;

import mobile.attendance.attendance.Attendance;

import java.util.List;
import java.util.Optional;

public interface AttendanceRepository {

    Attendance save(Attendance attendance);

    List<Attendance> findAll();

    Optional<Attendance> findById(final Long id);

    int update(final Attendance attendance);

    int delete(final Long id);
}
