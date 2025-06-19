package mobile.attendance.attendance;

import java.sql.Date;
import java.time.LocalDate;

public class AttendanceSearchCondition {
    private LocalDate attendanceDate;
    private String memo;

    public AttendanceSearchCondition() {}

    public AttendanceSearchCondition(final String memo, final LocalDate attendanceDate) {
        this.memo = memo;
        this.attendanceDate = attendanceDate;
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

    @Override
    public String toString() {
        return "AttendanceSearchCondition{" +
                "attendanceDate=" + attendanceDate +
                ", memo='" + memo + '\'' +
                '}';
    }
}
