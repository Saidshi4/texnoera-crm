package com.example.texnoeracrm.controller;

import com.example.texnoeracrm.model.get.TaskGetDto;
import com.example.texnoeracrm.model.get.UserTaskGetDto;
import com.example.texnoeracrm.model.set.TaskSetDto;
import com.example.texnoeracrm.service.TaskService;
import com.example.texnoeracrm.service.auth.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final JwtService jwtService;

    @PostMapping("/groups/{groupId}")
    public void creatTasks(@PathVariable Long groupId, @RequestBody TaskSetDto taskSetDto){
        taskService.createTask(groupId, taskSetDto);
    }

    @GetMapping("/groups/{groupId}")
    public List<TaskGetDto> getTasks(HttpServletRequest request, @PathVariable Long groupId){
        String token = (String) request.getAttribute("token");
        Long userId = jwtService.extractUserIdFromAccessToken(token, true);
        return taskService.findTaskByUserIdAndGroupId(userId, groupId);
    }

}
