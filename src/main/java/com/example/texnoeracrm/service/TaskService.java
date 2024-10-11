package com.example.texnoeracrm.service;

import com.example.texnoeracrm.dao.entity.*;
import com.example.texnoeracrm.dao.repository.*;
import com.example.texnoeracrm.enums.ExceptionEnum;
import com.example.texnoeracrm.enums.RoleEnum;
import com.example.texnoeracrm.enums.TaskStatusEnum;
import com.example.texnoeracrm.exception.NotFoundException;
import com.example.texnoeracrm.mapper.TaskMapper;
import com.example.texnoeracrm.model.get.TaskGetDto;
import com.example.texnoeracrm.model.set.TaskSetDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final GroupRepository groupRepository;
    private final UserTaskRepository userTaskRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

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

    private GroupEntity findGroupById(Long groupId) {
        log.info("ActionLog.findGroupById.start groupId {}", groupId);
        GroupEntity groupEntity = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(
                        ExceptionEnum.GROUP_NOT_FOUND.name(),
                        String.format(ExceptionEnum.GROUP_NOT_FOUND.getLog(), groupId)
                ));
        log.info("ActionLog.findGroupById.end groupId {}", groupId);
        return groupEntity;
    }

    public void createTask(Long groupId, TaskSetDto taskSetDto) {
        log.info("ActionLog.createTask.start");
        GroupEntity groupEntity = findGroupById(groupId);
        TaskEntity taskEntity = taskMapper.mapToEntity(taskSetDto);
        taskEntity.setCreatedAt(LocalDateTime.now());
        taskEntity.setDeadlineDay(taskSetDto.getDate());
        taskEntity.setDeadlineTime(taskSetDto.getTime());
        taskEntity.setGroupEntity(groupEntity);
        taskRepository.save(taskEntity);
        List<UserEntity> userEntities = userRepository.findByGroupId(groupId);
        userEntities.forEach(
                userEntity -> {
                    if (userEntity.getRoleEntity().getName() == RoleEnum.STUDENT) {
                        NotificationEntity notification = NotificationEntity.builder()
                                .title("New Task Created")
                                .content("A new task titled '" + taskSetDto.getName()
                                        + "' has been assigned to your group. Deadline: " + taskSetDto.getDate()
                                        + " at " + taskSetDto.getTime() + ".")
                                .userEntity(userEntity)
                                .build();
                        notificationRepository.save(notification);

                        UserTaskEntity userTask = UserTaskEntity.builder()
                                .userEntity(userEntity)
                                .taskEntity(taskEntity)
                                .status(TaskStatusEnum.CREATED)
                                .build();
                        userTaskRepository.save(userTask);
                    }
                }
        );
        log.info("ActionLog.createTask.end");
    }

    public List<TaskGetDto> findTaskByStudentIdAndGroupId(Long userId, Long groupId) {
        log.info("ActionLog.findTaskByStudentIdAndGroupId.start");
        List<TaskEntity> taskEntities = taskRepository.findByGroupIdAndUserId(groupId, userId);
        List<TaskGetDto> taskGetDtos = new ArrayList<>();
        taskEntities.forEach(taskEntity -> taskEntity.getUserTaskEntities().forEach(
                userTaskEntity -> {
                    if (Objects.equals(userTaskEntity.getUserEntity().getId(), userId)) {
                        taskGetDtos.add(TaskGetDto.builder()
                                .id(taskEntity.getId())
                                .name(taskEntity.getName())
                                .deadlineDay(taskEntity.getDeadlineDay())
                                .deadlineTime(taskEntity.getDeadlineTime())
                                .description(taskEntity.getDescription())
                                .createdAt(taskEntity.getCreatedAt())
                                .build());
                    }
                }
        ));
        log.info("ActionLog.findTaskByStudentIdAndGroupId.end");
        return taskGetDtos;
    }

    public List<TaskGetDto> findTaskByTeacherGroupId(Long groupId) {
        log.info("ActionLog.findTaskByTeacherGroupId.start");
        List<TaskEntity> taskEntities = taskRepository.findByGroupId(groupId);
        List<TaskGetDto> taskGetDtos = taskMapper.mapToDtos(taskEntities);
        log.info("ActionLog.findTaskByTeacherGroupId.end");
        return taskGetDtos;
    }

}
