package mobile.attendance.config;

import mobile.attendance.attendance.AttendanceController;
import mobile.attendance.attendance.AttendanceService;
import mobile.attendance.attendance.repository.AttendanceRepository;
import mobile.attendance.attendance.repository.JdbcTemplateAttendanceRepository;
import mobile.attendance.attendanceLog.AttendanceLogController;
import mobile.attendance.attendanceLog.AttendanceLogService;
import mobile.attendance.attendanceLog.repository.AttendanceLogRepository;
import mobile.attendance.attendanceLog.repository.JdbcTemplateAttendanceLogRepository;
import mobile.attendance.user.UserService;
import mobile.attendance.user.repository.JdbcTemplateUserRepository;
import mobile.attendance.user.repository.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JdbcTemplateConfig {
    private final DataSource dataSource;

    public JdbcTemplateConfig(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public UserRepository userRepository() {
        return new JdbcTemplateUserRepository(dataSource);
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository());
    }

    @Bean
    public AttendanceRepository attendanceRepository() {
        return new JdbcTemplateAttendanceRepository(dataSource);
    }

    @Bean
    public AttendanceService attendanceService() {
        return new AttendanceService(attendanceRepository());
    }

    @Bean
    public AttendanceLogRepository attendanceLogRepository() {
        return new JdbcTemplateAttendanceLogRepository(dataSource);
    }

    @Bean
    public AttendanceLogService attendanceLogService() {
        return new AttendanceLogService(attendanceLogRepository(), attendanceRepository(), userRepository());
    }

    @Bean
    public AttendanceLogController attendanceLogController() {
        return new AttendanceLogController(attendanceLogService());
    }
}

