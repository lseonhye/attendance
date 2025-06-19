package mobile.attendance.attendance;

import java.sql.Date;

public class AttendanceSearchCondition {
    private Date attendanceDate;
    private String memo;

    public AttendanceSearchCondition(final String memo, final Date attendanceDate) {
        this.memo = memo;
        this.attendanceDate = attendanceDate;
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

    @Override
    public String toString() {
        return "AttendanceSearchCondition{" +
                "attendanceDate=" + attendanceDate +
                ", memo='" + memo + '\'' +
                '}';
    }
}
