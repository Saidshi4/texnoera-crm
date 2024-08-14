package com.example.texnoeracrm.service;

import com.example.texnoeracrm.dao.entity.TaskEntity;
import com.example.texnoeracrm.dao.repository.TaskRepository;
import com.example.texnoeracrm.enums.ExceptionEnum;
import com.example.texnoeracrm.exception.NotFoundException;
import com.example.texnoeracrm.mapper.TaskMapper;
import com.example.texnoeracrm.model.get.TaskGetDto;
import com.example.texnoeracrm.model.set.TaskSetDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskEntity findById(Long taskId) {
        log.info("ActionLog.taskFindById.start taskId{}", taskId);
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException(
                        ExceptionEnum.TASK_NOT_FOUND.name(),
                        String.format(ExceptionEnum.TASK_NOT_FOUND.getLog(), taskId)
                ));
        log.info("ActionLog.taskFindById.end taskId {}", taskId);
        return taskEntity;
    }

    public void createTask(TaskSetDto taskSetDto) {
        log.info("ActionLog.createTask.start");
        TaskEntity taskEntity = taskMapper.mapToEntity(taskSetDto);
        taskEntity.setCreated_at(LocalDateTime.now());
        taskRepository.save(taskEntity);
        log.info("ActionLog.createTask.end");
    }

    public TaskGetDto getTask(Long taskId) {
        log.info("ActionLog.getTask.start taskId {}", taskId);
        TaskEntity taskEntity = findById(taskId);
        TaskGetDto groupGetDto = taskMapper.mapToDto(taskEntity);
        log.info("ActionLog.getGroup.end taskId {}", taskId);
        return groupGetDto;
    }

    public List<TaskGetDto> getAllTasks() {
        log.info("ActionLog.getAllGroups.start");
        List<TaskEntity> taskEntities = taskRepository.findAll();
        List<TaskGetDto> taskGetDtos = taskMapper.mapToDtos(taskEntities);
        log.info("ActionLog.getAllGroups.end");
        return taskGetDtos;
    }


}
