package com.example.texnoeracrm.service;

import com.example.texnoeracrm.dao.entity.NotificationEntity;
import com.example.texnoeracrm.dao.entity.TaskEntity;
import com.example.texnoeracrm.dao.entity.UserTaskEntity;
import com.example.texnoeracrm.dao.repository.NotificationRepository;
import com.example.texnoeracrm.dao.repository.TaskRepository;
import com.example.texnoeracrm.dao.repository.UserTaskRepository;
import com.example.texnoeracrm.enums.TaskStatusEnum;
import com.example.texnoeracrm.mapper.UserTaskMapper;
import com.example.texnoeracrm.model.get.UserTaskGetByTeacherDto;
import com.example.texnoeracrm.model.get.UserTaskGetDto;
import com.example.texnoeracrm.model.set.TaskGradeSetDto;
import com.example.texnoeracrm.model.set.UserTaskSetDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserTaskService {

    private final UserTaskRepository userTaskRepository;
    private final UserTaskMapper userTaskMapper;
    private final TaskRepository taskRepository;
    private final NotificationRepository notificationRepository;

    private UserTaskEntity findById(Long id) {
        return userTaskRepository.findById(id).orElse(null);
    }

    public UserTaskGetDto getTask(Long taskId, Long userId) {
        log.info("ActionLog.getUserTask.start taskId {} and userId {}", taskId, userId);
        UserTaskEntity userTaskEntity = userTaskRepository.findByTaskIdAndUserId(taskId, userId);
        if (userTaskEntity.getStatus().equals(TaskStatusEnum.CREATED)){
            userTaskEntity.setStatus(TaskStatusEnum.IN_PROGRESS);
        }
        userTaskRepository.save(userTaskEntity);
        UserTaskGetDto userTaskGetDto = userTaskMapper.mapToDto(userTaskEntity);
        log.info("ActionLog.getUserTask.end taskId {} and userId {}", taskId, userId);
        return userTaskGetDto;
    }

    public UserTaskGetDto doTask(Long userId, Long taskId, UserTaskSetDto userTaskSetDto){
        log.info("ActionLog.doTask.start");
        UserTaskEntity userTaskEntity = userTaskRepository.findByTaskIdAndUserId(taskId, userId);
        userTaskEntity.setAnswer(userTaskSetDto.getAnswer());
        userTaskEntity.setAnswerTime(LocalDateTime.now());
        if (userTaskEntity.getStatus() == TaskStatusEnum.IN_PROGRESS) {
            userTaskEntity.setStatus(TaskStatusEnum.DONE);
        } else if (userTaskEntity.getStatus() == TaskStatusEnum.EXPIRED) {
            userTaskEntity.setStatus(TaskStatusEnum.DELAYED);
        }
        userTaskRepository.save(userTaskEntity);
        UserTaskGetDto userTaskGetDto = userTaskMapper.mapToDto(userTaskEntity);
        log.info("ActionLog.doTask.end");
        return userTaskGetDto;
    }

    public UserTaskGetDto updateTask(Long userId, Long taskId, UserTaskSetDto userTaskSetDto){
        log.info("ActionLog.updateTask.start");
        LocalDateTime now = LocalDateTime.now();
        UserTaskEntity userTask = userTaskRepository.findByTaskIdAndUserId(taskId, userId);
        userTask.setAnswer(userTaskSetDto.getAnswer());
        userTask.setAnswerTime(now);
        if (userTask.getTaskEntity().getDeadlineDay().isBefore(now.toLocalDate()) ||
                (userTask.getTaskEntity().getDeadlineDay().isEqual(now.toLocalDate()) &&
                        userTask.getTaskEntity().getDeadlineTime().isBefore(now.toLocalTime()))) {
            userTask.setStatus(TaskStatusEnum.DELAYED);
        } else {
            userTask.setStatus(TaskStatusEnum.DONE);
        }
        userTaskRepository.save(userTask);
        UserTaskGetDto userTaskGetDto = userTaskMapper.mapToDto(userTask);
        log.info("ActionLog.updateTask.end");
        return userTaskGetDto;
    }

    @Transactional
    public void checkDeadline(){
        log.info("ActionLog.checkDeadline.start");
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        LocalTime start = time.minusMinutes(1);
        LocalTime end = time.plusMinutes(1);

        List<TaskEntity> taskEntities = taskRepository.findByDeadline(date, start, end);

        taskEntities.forEach(taskEntity -> {
            List<UserTaskEntity> userTasks = userTaskRepository.findByTaskId(taskEntity.getId());
            userTasks.forEach(
                    userTask -> {
                        if (userTask.getStatus() != TaskStatusEnum.DONE) {
                            userTask.setStatus(TaskStatusEnum.EXPIRED);
                        }
                    }
            );
        });
        log.info("ActionLog.checkDeadline.end");
    }

    public List<UserTaskGetByTeacherDto> getUserTaskByTeacher(Long groupId, Long taskId) {
        log.info("ActionLog.getUserTaskByTeacher.start groupId {}", groupId);
        List<UserTaskEntity> userTasks = userTaskRepository.findByTaskId(taskId);
        List<UserTaskGetByTeacherDto> userTaskGetByTeacherDtos = userTaskMapper.mapToGetByTeacherDtos(userTasks);
        log.info("ActionLog.getUserTaskByTeacher.end groupId {}", groupId);
        return userTaskGetByTeacherDtos;
    }

    public Integer gradeTask(Long userId, Long taskId, TaskGradeSetDto gradeSetDto){
        log.info("ActionLog.gradeTask.start");
        UserTaskEntity userTaskEntity = userTaskRepository.findByTaskIdAndUserId(taskId, userId);
        NotificationEntity notification = NotificationEntity.builder()
                .title("Task Graded")
                .content("Your task '" + userTaskEntity.getTaskEntity().getName() + "' has been graded. You received a grade of: "
                        + gradeSetDto.getGrade() + ".")
                .userEntity(userTaskEntity.getUserEntity())
                .build();
        userTaskEntity.setGrade(gradeSetDto.getGrade());
        notificationRepository.save(notification);
        userTaskRepository.save(userTaskEntity);
        log.info("ActionLog.gradeTask.end");
        return gradeSetDto.getGrade();
    }

    public UserTaskGetDto getStudentTask(Long userTaskId){
        log.info("ActionLog.getStudentTask.start");
        UserTaskEntity userTaskEntity = findById(userTaskId);
        UserTaskGetDto userTaskGetDto = userTaskMapper.mapToDto(userTaskEntity);
        log.info("ActionLog.getStudentTask.end");
        return userTaskGetDto;
    }



}