package mobile.attendance.attendance;

import java.sql.Date;
import java.time.LocalDate;

public class AttendanceRequest {
    private Long attendanceId;
    private LocalDate attendanceDate;
    private String memo;

    public Long getAttendanceId() {
        return attendanceId;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public String getMemo() {
        return memo;
    }
}
