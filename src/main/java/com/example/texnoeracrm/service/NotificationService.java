package com.example.texnoeracrm.service;

import com.example.texnoeracrm.dao.entity.AttendanceEntity;
import java.time.temporal.ChronoUnit;
import com.example.texnoeracrm.dao.entity.GroupEntity;
import com.example.texnoeracrm.dao.entity.UserEntity;
import com.example.texnoeracrm.dao.repository.AttendanceRepository;
import com.example.texnoeracrm.dao.repository.GroupRepository;
import com.example.texnoeracrm.dao.repository.UserRepository;
import com.example.texnoeracrm.enums.ExceptionEnum;
import com.example.texnoeracrm.enums.RoleEnum;
import com.example.texnoeracrm.exception.NotFoundException;
import com.example.texnoeracrm.model.get.Note;
import com.google.firebase.messaging.FirebaseMessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
import org.hibernate.Hibernate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final AttendanceRepository attendanceRepository;
    private final FirebaseMessagingService firebaseMessagingService;

    public UserEntity findUserById(Long userId) {
        log.info("ActionLog.findUserById.start userId {}", userId);
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        ExceptionEnum.USER_NOT_FOUND.name(),
                        String.format(ExceptionEnum.USER_NOT_FOUND.getLog(), userId)
                ));
        log.info("ActionLog.findUserById.end userId {}", userId);
        return userEntity;
    }
    @Transactional
    public void checkLessonStartTime() {
        log.info("ActionLog.checkLessonStartTime.start");

        LocalDateTime now = LocalDateTime.now();
        DayOfWeek today = now.getDayOfWeek();

        LocalTime startTime = now.toLocalTime().minusMinutes(31);
        LocalTime endTime = startTime.plusMinutes(2);

        List<GroupEntity> groupEntities = groupRepository.findGroupsForNotification(today.getValue() - 1, startTime, endTime);

        groupEntities.forEach(groupEntity -> {
            groupEntity.getUserEntities().stream()
                    .filter(userEntity -> userEntity.getRoleEntity().getName().equals(RoleEnum.TEACHER))
                    .findFirst()
                    .ifPresent(teacherEntity -> {
                        AttendanceEntity attendanceEntity = attendanceRepository.findLastByGroupEntityAndUserEntity(groupEntity, teacherEntity);
                        if (attendanceEntity == null || attendanceEntity.getCreatedAt() == null || attendanceEntity.getCreatedAt().isBefore(now.minusMinutes(30))) {
                            Note note = Note.builder()
                                    .subject("Please enter the attendance!")
                                    .content("The lesson has already started for 30 minutes.")
                                    .build();
                            sendNotification(teacherEntity, note);
                        }
                    });
        });

        log.info("ActionLog.checkLessonStartTime.end");
    }


    @Transactional
    public void checkLessonEndTime() {
        log.info("ActionLog.checkLessonEndTime.start");

        LocalDateTime now = LocalDateTime.now();
        DayOfWeek today = now.getDayOfWeek();
        LocalTime startTime = now.toLocalTime().plusMinutes(14);
        LocalTime endTime = startTime.plusMinutes(2);

        List<GroupEntity> groupEntities = groupRepository.findGroupsByDayAndEndTime(today.getValue() - 1, startTime, endTime);

        groupEntities.forEach(groupEntity -> {
            groupEntity.getUserEntities().stream()
                    .filter(userEntity -> userEntity.getRoleEntity().getName().equals(RoleEnum.TEACHER))
                    .findFirst()
                    .ifPresent(teacherEntity -> {
                        AttendanceEntity attendanceEntity = attendanceRepository.findLastByGroupEntityAndUserEntity(groupEntity, teacherEntity);
                        if (attendanceEntity == null || attendanceEntity.getCreatedAt() == null || attendanceEntity.getCreatedAt().isAfter(now.plusMinutes(15))) {
                            Note note = Note.builder()
                                    .subject("Please enter the attendance!")
                                    .content("The lesson will end in 15 minutes.")
                                    .build();
                            System.out.println(teacherEntity.getName());
                            sendNotification(teacherEntity, note);
                        }
                    });
        });
        log.info("ActionLog.checkLessonEndTime.end");
    }

    @Transactional
    public void sendNotification(UserEntity teacherEntity, Note note) {
        log.info("ActionLog.sendNotification.start teacher {}", teacherEntity.getName());
        Hibernate.initialize(teacherEntity.getDeviceTokenEntities());
        teacherEntity.getDeviceTokenEntities().forEach(deviceTokenEntity -> {
            try {
                firebaseMessagingService.sendNotification(note, deviceTokenEntity.getToken());
            } catch (FirebaseMessagingException e) {
                throw new RuntimeException(e);
            }
        });
        log.info("ActionLog.sendNotification.end");
    }

}
