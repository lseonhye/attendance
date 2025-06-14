package mobile.attendance.attendanceLog;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/attendance_log")
public class AttendanceLogController {

    AttendanceLogService attendanceLogService;

    @Autowired
    public AttendanceLogController(final AttendanceLogService attendanceLogService) {
        this.attendanceLogService = attendanceLogService;
    }

    @PostMapping
    public ResponseEntity<AttendanceLog> createAttendanceLog(@RequestBody final AttendanceLogRequest request) {
        AttendanceLog attendanceLog = new AttendanceLog(
                request.getUserId(), request.getAttendanceId());
        attendanceLog.setNote("");

        AttendanceLog saved = attendanceLogService.createAttendanceLog(attendanceLog);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<AttendanceLog>> getAttendances() {
        return ResponseEntity.ok(attendanceLogService.findAllAttendanceLogs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceLog> getAttendance(@PathVariable final Long id) {
        Optional<AttendanceLog> attendanceLog = attendanceLogService.findById(id);
        return attendanceLog.isPresent() ? ResponseEntity.ok(attendanceLog.get()) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateAttendance(@PathVariable("id") final Long logId,
                                                   @RequestBody final AttendanceLogRequest request) {
        AttendanceLog updateAttendance = new AttendanceLog(logId, request.getNote());
        attendanceLogService.updateAttendanceLog(updateAttendance);
        return ResponseEntity.ok("성공적으로 기록 노트가 수정되었습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeAttendance(@PathVariable("id") final Long id) {
        attendanceLogService.removeAttendanceLog(id);
        return ResponseEntity.ok("성공적으로 기록 노트가 삭제되었습니다.");
    }

    @PostMapping("/create_logs")
    public ResponseEntity<String> createAttendanceLogs(@RequestBody final AttendanceLogRequest request) {
        try {

            AttendanceLog attendanceLog = new AttendanceLog(request.getUserId(), request.getAttendanceId());

            // @Scheduled 메서드 수동 호출
            attendanceLogService.createAttendanceLog(attendanceLog);
            return ResponseEntity.ok("Scheduled task manually triggered!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error triggering scheduled task");
        }
    }

}
