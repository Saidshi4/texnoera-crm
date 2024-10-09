package com.example.texnoeracrm.controller;

import com.example.texnoeracrm.model.get.UserTaskGetDto;
import com.example.texnoeracrm.model.set.UserSetDto;
import com.example.texnoeracrm.model.set.UserTaskSetDto;
import com.example.texnoeracrm.service.UserTaskService;
import com.example.texnoeracrm.service.auth.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/userTasks")
@RestController
@RequiredArgsConstructor
public class UserTaskController {

    private final UserTaskService userTaskService;
    private final JwtService jwtService;

    @PatchMapping("/{taskId}")
    public UserTaskGetDto getTask(HttpServletRequest request, @PathVariable Long taskId){
        String token = (String) request.getAttribute("token");
        Long userId = jwtService.extractUserIdFromAccessToken(token, true);
        return userTaskService.getTask(taskId, userId);
    }

    @PatchMapping("/do/{taskId}")
    public UserTaskGetDto doTask(HttpServletRequest request, @PathVariable Long taskId, @RequestBody UserTaskSetDto userTaskSetDto){
        String token = (String) request.getAttribute("token");
        Long userId = jwtService.extractUserIdFromAccessToken(token, true);
        return userTaskService.doTask(userId, taskId, userTaskSetDto);
    }

    @PatchMapping("/update/{taskId}")
    public UserTaskGetDto updateTask(HttpServletRequest request, @PathVariable Long taskId, @RequestBody UserTaskSetDto userTaskSetDto){
        String token = (String) request.getAttribute("token");
        Long userId = jwtService.extractUserIdFromAccessToken(token, true);
        return userTaskService.updateTask(userId, taskId, userTaskSetDto);
    }

    @GetMapping("/demo")
    public void getDemo(HttpServletRequest request){
        userTaskService.checkDeadline();
    }

}
