package com.example.texnoeracrm.scheduler;

import com.example.texnoeracrm.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AttendanceSchedulerService {
    private final AttendanceService attendanceService;

    @Scheduled(cron = "0 1 0 * * *")
    public void createAttendance() {
        attendanceService.createAttendances();
    }
}
