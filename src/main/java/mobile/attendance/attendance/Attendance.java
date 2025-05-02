package mobile.attendance.attendance;

import java.sql.Date;

public class Attendance {
    private Long attendanceId;
    private Date attendanceDate;
    private String memo;

    public Attendance() {}

    public Attendance(final Date attendanceDate, final String memo) {
        this.attendanceDate = attendanceDate;
        this.memo = memo;
    }

    public Attendance(final Long attendanceId, final java.sql.Date attendanceDate, final String memo) {
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

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(final Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(final String memo) {
        this.memo = memo;
    }
}
