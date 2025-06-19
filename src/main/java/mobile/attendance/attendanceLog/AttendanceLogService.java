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

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public AttendanceLog createAttendanceLog(final AttendanceLog attendanceLog) {
        if (attendanceLog.getCheckInAt() == null) {
            attendanceLog.setCheckInAt(Timestamp.valueOf(LocalDateTime.now())); // 자동 시간 입력
            attendanceLog.setPresent(true); // 출석으로 간주
        }
        return attendanceLogRepository.save(attendanceLog);
    }

    public List<AttendanceLog> findAllAttendanceLogs() {
        return attendanceLogRepository.findAll();
    }

    public Optional<AttendanceLog> findById(final Long id) {
        return attendanceLogRepository.findById(id);
    }

    public AttendanceLog updateAttendanceLog(AttendanceLog log) {

        int updateCount = attendanceLogRepository.update(log);

        if (updateCount == 1) {
            return attendanceLogRepository.findById(log.getLogId())
                    .orElseThrow(() ->
                            new IllegalStateException("update 후 조회 실패"));
        }
        throw new IllegalArgumentException("해당 로그가 없습니다");
    }
    public int removeAttendanceLog(final Long id) {
        return attendanceLogRepository.delete(id);
    }

    @Scheduled(cron = "0 14 16 * * ?")
    public void createAttendanceLogs() {

        logger.info("Scheduled task started");

        LocalDate today = LocalDate.now();
        logger.info("Today's date: " + today);


        Optional<Attendance> optionalAttendance = attendanceRepository.findByDate(today);
        Attendance attendance = optionalAttendance.orElseGet(() -> {
            Attendance a = new Attendance();
            a.setAttendanceDate(today);
            return attendanceRepository.save(a);
        });


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

    @Scheduled(cron = "0 5 0 * * ?") // 매일 자정 5분
    @Transactional
    public void markAbsentees() {
        LocalDate yesterday = LocalDate.now().minusDays(1); // 전날

        Optional<Attendance> optionalAttendance = attendanceRepository.findByDate(yesterday);
        if (optionalAttendance.isEmpty()) {
            logger.info("어제 출석 기록 없음");
            return;
        }

        Attendance attendance = optionalAttendance.get();
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

    @Transactional
    public AttendanceLog updateCheckoutTime(Long id) {
        AttendanceLog log = attendanceLogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 로그를 찾을 수 없습니다."));

        if (log.getCheckOutAt() == null) {
            log.setCheckOutAt(Timestamp.valueOf(LocalDateTime.now())); // 현재 시간으로 체크아웃
            attendanceLogRepository.updateCheckoutTime(log); // DB에 업데이트
            return log;
        } else {
            throw new IllegalStateException("이미 체크아웃된 로그입니다.");
        }
    }
    public Duration calculateDuration(Long id) {
        AttendanceLog log = attendanceLogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 출석 로그가 존재하지 않습니다."));

        if (log.getCheckInAt() == null || log.getCheckOutAt() == null) {
            throw new IllegalStateException("입실 또는 퇴실 시간이 존재하지 않습니다.");
        }

        return Duration.between(
                log.getCheckInAt().toLocalDateTime(),
                log.getCheckOutAt().toLocalDateTime()
        );
    }

}
