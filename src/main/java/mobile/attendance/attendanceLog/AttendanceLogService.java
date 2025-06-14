package mobile.attendance.attendanceLog;

import mobile.attendance.attendance.Attendance;
import mobile.attendance.attendance.repository.AttendanceRepository;
import mobile.attendance.attendanceLog.repository.AttendanceLogRepository;
import mobile.attendance.user.User;
import mobile.attendance.user.repository.JdbcTemplateUserRepository;
import mobile.attendance.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class AttendanceLogService {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(JdbcTemplateUserRepository.class);

    private final AttendanceLogRepository attendanceLogRepository;
    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;

    public AttendanceLogService(AttendanceLogRepository attendanceLogRepository, final AttendanceRepository attendanceRepository, final UserRepository userRepository)
    {
        this.attendanceLogRepository=attendanceLogRepository;
        this.attendanceRepository = attendanceRepository;
        this.userRepository = userRepository;
    }

    public AttendanceLog createAttendanceLog(final AttendanceLog attendance) {
        return attendanceLogRepository.save(attendance);
    }

    public List<AttendanceLog> findAllAttendanceLogs() {
        return attendanceLogRepository.findAll();
    }

    public Optional<AttendanceLog> findById(final Long id) {
        return attendanceLogRepository.findById(id);
    }

    public int updateAttendanceLog(final AttendanceLog attendanceLog) {
        return attendanceLogRepository.update(attendanceLog);
    }

    public int removeAttendanceLog(final Long id) {
        return attendanceLogRepository.delete(id);
    }

    @Scheduled(cron = "0 14 16 * * ?")
    public void createAttendanceLogs() {

        logger.info("Scheduled task started");

        LocalDate today = LocalDate.now();
        logger.info("Today's date: " + today);


        Attendance attendance = attendanceRepository.findByAttendanceDate(today);
        if (attendance == null) {
            attendance = new Attendance();
            attendance.setAttendanceDate(today);
            attendanceRepository.save(attendance);
            logger.info("New attendance created for today");
        }

        List<User> users = userRepository.findAll();
        for (User user : users) {
            AttendanceLog log = new AttendanceLog();
            log.setUserId(user.getUserId());
            log.setAttendanceId(attendance.getAttendanceId());
            log.setCheckInAt(null);
            log.setCheckOutAt(null);
            log.setPresent(false);
            log.setNote("");
            attendanceLogRepository.save(log);
            logger.info("Attendance log created for user: " + user.getUserId());
        }
        logger.info("Scheduled task completed");
    }
    @Scheduled(cron = "0 5 0 * * ?") // 매일 자정 5분 (0시 5분에 실행)
    @Transactional
    public void markAbsentees() {
        LocalDate yesterday = LocalDate.now().minusDays(1); // 전날

        Optional<Attendance> optionalAttendance = attendanceRepository.findByDate(yesterday);
        if (optionalAttendance.isEmpty()) {
            logger.info("어제 출석 기록 없음");
            return;
        }

        Attendance attendance = optionalAttendance.get();

        // 해당 날짜의 출석 로그 전부 가져오기
        List<AttendanceLog> logs = attendanceLogRepository.findAllByAttendanceId(attendance.getAttendanceId());

        for (AttendanceLog log : logs) {
            if (log.getCheckInAt() == null && (log.getNote() == null || log.getNote().isEmpty())) {
                log.setNote("결석");
                attendanceLogRepository.updateNote(log);
                logger.info("결석 처리: userId={}, attendanceId={}", log.getUserId(), log.getAttendanceId());
            }
        }

        logger.info("결석 자동 처리 완료");
    }

}
