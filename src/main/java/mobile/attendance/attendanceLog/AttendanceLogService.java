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
}
