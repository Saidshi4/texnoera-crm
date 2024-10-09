package com.example.texnoeracrm.scheduler;

import com.example.texnoeracrm.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationSchedulerService {
    private final NotificationService notificationService;

    @Scheduled(cron = "0 0,30 * * * *")
    public void checkLessonStartTime(){
        notificationService.checkLessonStartTime();
    }

    @Scheduled(cron = "0 15,45 * * * *")
    public void checkLessonEndTime(){
        notificationService.checkLessonEndTime();
    }

}
