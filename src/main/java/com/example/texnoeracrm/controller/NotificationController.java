package com.example.texnoeracrm.controller;

import com.example.texnoeracrm.model.get.NotificationGetDto;
import com.example.texnoeracrm.service.NotificationService;
import com.example.texnoeracrm.service.auth.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final JwtService jwtService;

    @GetMapping
    public List<NotificationGetDto> getAllNotificationsByUserId(HttpServletRequest request) {
        String token = (String) request.getAttribute("token");
        Long userId = jwtService.extractUserIdFromAccessToken(token, true);
        return notificationService.findALlByUserId(userId);
    }
}
