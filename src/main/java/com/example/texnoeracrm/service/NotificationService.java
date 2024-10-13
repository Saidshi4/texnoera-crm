package com.example.texnoeracrm.service;

import com.example.texnoeracrm.dao.entity.*;
import com.example.texnoeracrm.dao.repository.*;
import com.example.texnoeracrm.enums.ExceptionEnum;
import com.example.texnoeracrm.enums.RoleEnum;
import com.example.texnoeracrm.exception.NotFoundException;
import com.example.texnoeracrm.mapper.NotificationMapper;
import com.example.texnoeracrm.model.get.Note;
import com.example.texnoeracrm.model.get.NotificationGetDto;
import com.google.firebase.messaging.FirebaseMessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final AttendanceRepository attendanceRepository;
    private final FirebaseMessagingService firebaseMessagingService;
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;


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

    public Page<NotificationGetDto> findAllByUserId(Long userId, int page, int size) {
        log.info("ActionLog.findAllByUserId.start userId {}", userId);

        Pageable pageable = PageRequest.of(page, size);
        Page<NotificationEntity> notificationPage = notificationRepository.findByUserEntityId(userId, pageable);

        List<NotificationGetDto> notificationGetDtos = notificationMapper.mapToDtos(notificationPage.getContent());

        log.info("ActionLog.findAllByUserId.end userId {}", userId);
        return new PageImpl<>(notificationGetDtos, notificationPage.getPageable(), notificationPage.getTotalElements());
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
            List<UserEntity> userEntities = userRepository.findByGroupId(groupEntity.getId());
            userEntities.forEach(userEntity -> {
                if (userEntity.getRoleEntity().getName().equals(RoleEnum.TEACHER)){
                    AttendanceEntity attendanceEntity = attendanceRepository.findLastByGroupEntityAndUserEntity(groupEntity, userEntity);

                    if (attendanceEntity == null || attendanceEntity.getCreatedAt() == null || attendanceEntity.getCreatedAt().isBefore(now.minusMinutes(30))) {
                        Note note = Note.builder()
                                .subject("Please enter the attendance!")
                                .content("The lesson has already started for 30 minutes.")
                                .build();
                        System.out.println(userEntity.getName());
                        sendNotification(userEntity, note);
                    }
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
            List<UserEntity> userEntities = userRepository.findByGroupId(groupEntity.getId());
            userEntities.forEach(userEntity -> {
                if (userEntity.getRoleEntity().getName().equals(RoleEnum.TEACHER)){
                    AttendanceEntity attendanceEntity = attendanceRepository.findLastByGroupEntityAndUserEntity(groupEntity, userEntity);
                    if (attendanceEntity == null || attendanceEntity.getCreatedAt() == null || attendanceEntity.getCreatedAt().isAfter(now.plusMinutes(15))) {
                        Note note = Note.builder()
                                .subject("Please enter the attendance!")
                                .content("The lesson will end in 15 minutes.")
                                .build();
                        System.out.println(userEntity.getName());
                        sendNotification(userEntity, note);

                    }
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
