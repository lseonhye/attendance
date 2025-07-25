package mobile.attendance.attendanceLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
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
    public ResponseEntity<AttendanceLog> updateAttendance(
            @PathVariable Long id,
            @RequestBody AttendanceLogRequest req) {
        AttendanceLog updated = attendanceLogService.updateAttendanceLog(new AttendanceLog(id, req.getNote()));
        return ResponseEntity.ok(updated);
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
            attendanceLogService.createAttendanceLog(attendanceLog);
            return ResponseEntity.ok("Scheduled task manually triggered!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error triggering scheduled task");
        }
    }

    @PostMapping("/mark_absentees")
    public ResponseEntity<String> markAbsenteesManually() {
        try {
            attendanceLogService.markAbsentees(); // 스케줄러 로직 수동 실행
            return ResponseEntity.ok("결석 처리 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("결석 처리 중 오류 발생: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}/checkout")
    public ResponseEntity<?> checkout(@PathVariable("id") final Long id) {
        try {
            AttendanceLog updatedLog = attendanceLogService.updateCheckoutTime(id);
            return ResponseEntity.ok(updatedLog);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/duration")
    public ResponseEntity<?> getAttendanceDuration(@PathVariable("id") final Long id) {
        try {
            Duration duration = attendanceLogService.calculateDuration(id);
            long hours = duration.toHours();
            long minutes = duration.toMinutes() % 60;

            return ResponseEntity.ok(String.format("출석 시간: %d시간 %d분", hours, minutes));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/test/create-logs")
    public ResponseEntity<String> createLogsNow(){
        attendanceLogService.createAttendanceLogs();   // 스케줄러 메서드 직접 호출
        return ResponseEntity.ok("OK – logs created");
    }

}
