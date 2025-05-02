package mobile.attendance.attendance;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    public ResponseEntity<Attendance> createAttendance(@RequestBody final AttendanceRequest request) {
        Attendance attendance = new Attendance(
                request.getAttendanceId(), request.getAttendanceDate(), request.getMemo());
        return ResponseEntity.ok(attendanceService.createAttendance(attendance));
    }

    @GetMapping
    public ResponseEntity<List<Attendance>> getAttendances() {
        return ResponseEntity.ok(attendanceService.findAllAttendances());
    }

    @GetMapping
    public ResponseEntity<Attendance> getAttendance(@RequestBody final AttendanceRequest request) {
        return ResponseEntity.ok(attendanceService.findById(request.getAttendanceId()).get());
    }

    @PatchMapping
    public ResponseEntity<String> updateAttendance(@PathVariable("id") final Long id,
                                                   @RequestBody final AttendanceRequest request) {
        Attendance updateAttendance = new Attendance(id, request.getAttendanceDate(), request.getMemo());
        attendanceService.updateAttendance(updateAttendance);
        return ResponseEntity.ok("성공적으로 출석부 메모가 수정되었습니다.");
    }

    @DeleteMapping
    public ResponseEntity<String> removeAttendance(@PathVariable("id") final Long id) {
        attendanceService.removeAttendance(id);
        return ResponseEntity.ok("성공적으로 출석부 정보가 삭제되었습니다.");
    }
}
