package com.example.texnoeracrm.controller;

import com.example.texnoeracrm.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/register-device/{newDeviceToken}/users/{userId}")
    public void registerDevice(@PathVariable String newDeviceToken, @PathVariable Long userId) {
        notificationService.registerDeviceToken(newDeviceToken, userId);
    }

    @GetMapping
    public void sendNotification() {
        notificationService.checkLessonTime();
    }
}