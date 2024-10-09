package com.example.texnoeracrm.scheduler;

import com.example.texnoeracrm.service.NotificationService;
import com.example.texnoeracrm.service.TaskService;
import com.example.texnoeracrm.service.UserTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TaskSchedulerService {
    private final UserTaskService userTaskService;

    @Scheduled(cron = "0 0,30 * * * *")
    public void checkTaskDeadline(){
        userTaskService.checkDeadline();
    }


}
