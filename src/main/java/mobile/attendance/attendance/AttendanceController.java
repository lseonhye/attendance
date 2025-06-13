package mobile.attendance.attendance;

import mobile.attendance.attendanceLog.AttendanceLog;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    public ResponseEntity<?> createAttendance(@RequestBody final AttendanceRequest request) {
        try {
            Attendance attendance = new Attendance(request.getAttendanceDate(), request.getMemo());
            return ResponseEntity.ok(attendanceService.createAttendance(attendance));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Attendance>> getAttendances() {
        return ResponseEntity.ok(attendanceService.findAllAttendances());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attendance> getAttendanceById(@PathVariable final Long id) {
        Optional<Attendance> attendance = attendanceService.findById(id);

        return attendance.isPresent() ? ResponseEntity.ok(attendance.get()) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateAttendance(@PathVariable("id") final Long id,
                                                   @RequestBody final AttendanceRequest request) {
        Attendance updateAttendance = new Attendance(id, request.getAttendanceDate(), request.getMemo());
        attendanceService.updateAttendance(updateAttendance);
        return ResponseEntity.ok("성공적으로 출석부 메모가 수정되었습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeAttendance(@PathVariable("id") final Long id) {
        attendanceService.removeAttendance(id);
        return ResponseEntity.ok("성공적으로 출석부 정보가 삭제되었습니다.");
    }
}
