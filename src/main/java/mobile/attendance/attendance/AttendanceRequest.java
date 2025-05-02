package mobile.attendance.attendance;

import java.sql.Date;

public class AttendanceRequest {
    private Long attendanceId;
    private Date attendanceDate;
    private String memo;

    public AttendanceRequest() {}

    public AttendanceRequest(final Date attendanceDate, final String memo) {
        this.attendanceDate = attendanceDate;
        this.memo = memo;
    }

    public Long getAttendanceId() {
        return attendanceId;
    }

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
