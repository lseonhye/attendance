package mobile.attendance.attendanceLog;

import java.sql.Timestamp;

public class AttendanceLogRequest {
    private String userId;
    private Long attendanceId;
    private Timestamp checkInAt;
    private Timestamp checkOutAt;
    private boolean isPresent;
    private String note;

    public AttendanceLogRequest() {}

    public AttendanceLogRequest(final String userId, final Long attendanceId, final Timestamp checkInAt, final Timestamp checkOutAt, boolean isPresent, final String note) {
        this.userId = userId;
        this.attendanceId = attendanceId;
        this.checkInAt = checkInAt;
        this.checkOutAt = checkOutAt;
        this.isPresent = isPresent;
        this.note = note;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(final Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Timestamp getCheckInAt() {
        return checkInAt;
    }

    public void setCheckInAt(final Timestamp checkInAt) {
        this.checkInAt = checkInAt;
    }

    public Timestamp getCheckOutAt() {
        return checkOutAt;
    }

    public void setCheckOutAt(final Timestamp checkOutAt) {
        this.checkOutAt = checkOutAt;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(final boolean present) {
        isPresent = present;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }
}
