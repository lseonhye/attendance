package mobile.attendance.attendance;

import java.sql.Date;
import java.time.LocalDate;

public class Attendance {
    private Long attendanceId;
    private LocalDate attendanceDate;
    private String memo;

    public Attendance() {}

    public Attendance(final LocalDate attendanceDate, final String memo) {
        this.attendanceDate = attendanceDate;
        this.memo = memo;
    }

    public Attendance(final Long attendanceId, final LocalDate attendanceDate, final String memo) {
        this.attendanceId = attendanceId;
        this.attendanceDate = attendanceDate;
        this.memo = memo;
    }

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(final Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(final LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(final String memo) {
        this.memo = memo;
    }
}
