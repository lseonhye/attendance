package mobile.attendance.attendanceLog;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public class AttendanceLogController {

    AttendanceLogService attendanceLogService;

    public AttendanceLogController(AttendanceLogService attendanceLogService) {
        this.attendanceLogService = attendanceLogService;
    }

    @PostMapping
    public ResponseEntity<AttendanceLog> createAttendanceLog(@RequestBody final AttendanceLogRequest request) {
        AttendanceLog attendanceLog = new AttendanceLog(
                request.getUserId(), request.getAttendanceId());

        return ResponseEntity.ok(attendanceLogService.createAttendanceLog(attendanceLog));
    }

    @GetMapping
    public ResponseEntity<List<AttendanceLog>> getAttendances() {
        return ResponseEntity.ok(attendanceLogService.findAllAttendanceLogs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceLog> getAttendance(@RequestBody final Long id) {
        Optional<AttendanceLog> attendanceLog = attendanceLogService.findById(id);
        return attendanceLog.isPresent() ? ResponseEntity.ok(attendanceLog.get()) : ResponseEntity.notFound().build();
    }

    @PatchMapping
    public ResponseEntity<String> updateAttendance(@PathVariable("id") final Long logId,
                                                   @RequestBody final AttendanceLogRequest request) {
        AttendanceLog updateAttendance = new AttendanceLog(logId, request.getNote());
        attendanceLogService.updateAttendanceLog(updateAttendance);
        return ResponseEntity.ok("성공적으로 기록 노트가 수정되었습니다.");
    }

    @DeleteMapping
    public ResponseEntity<String> removeAttendance(@PathVariable("id") final Long id) {
        attendanceLogService.removeAttendanceLog(id);
        return ResponseEntity.ok("성공적으로 기록 노트가 삭제되었습니다.");
    }
}
