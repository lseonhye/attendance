package mobile.attendance.attendance;

import mobile.attendance.attendance.repository.AttendanceRepository;


import java.util.List;
import java.util.Optional;

public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    public AttendanceService(AttendanceRepository attendanceRepository)
    {
        this.attendanceRepository=attendanceRepository;
    }

    public Attendance createAttendance(final Attendance attendance) {
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
