package com.example.texnoeracrm.controller;

import com.example.texnoeracrm.model.get.NotificationGetDto;
import com.example.texnoeracrm.service.NotificationService;
import com.example.texnoeracrm.service.auth.JwtService;
import org.springframework.data.domain.Page;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final JwtService jwtService;

    @GetMapping
    public Page<NotificationGetDto> getAllNotificationsByUserId(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        String token = (String) request.getAttribute("token");
        Long userId = jwtService.extractUserIdFromAccessToken(token, true);
        return notificationService.findAllByUserId(userId,page, size);
    }
}
