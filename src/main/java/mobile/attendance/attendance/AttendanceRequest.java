package mobile.attendance.attendance;

import java.time.LocalDate;

public class AttendanceRequest {
    private LocalDate attendanceDate;
    private String memo;

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public String getMemo() {
        return memo;
    }
}
