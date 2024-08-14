package com.example.texnoeracrm.controller;

import com.example.texnoeracrm.model.get.TaskGetDto;
import com.example.texnoeracrm.model.set.TaskSetDto;
import com.example.texnoeracrm.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public void creatTasks(@RequestBody TaskSetDto taskSetDto){
        taskService.createTask(taskSetDto);
    }

    @GetMapping("/{taskId}")
    public TaskGetDto getTask(@PathVariable Long taskId){
        return taskService.getTask(taskId);
    }

    @GetMapping
    public List<TaskGetDto> getAllTasks(){
        return taskService.getAllTasks();
    }

}
