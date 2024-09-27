package com.example.texnoeracrm.service;

import com.example.texnoeracrm.dao.entity.AttendanceEntity;
import com.example.texnoeracrm.dao.entity.DeviceTokenEntity;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public void registerDeviceToken(String newDeviceToken, Long userId) {
        log.info("ActionLog.registerDeviceToken.start");
        UserEntity userEntity = findUserById(userId);
        boolean tokenExists = userEntity.getDeviceTokenEntities()
                .stream()
                .anyMatch(token -> token.getToken().equals(newDeviceToken));
        if (!tokenExists) {
            DeviceTokenEntity deviceTokenEntity = new DeviceTokenEntity();
            deviceTokenEntity.setToken(newDeviceToken);
            deviceTokenEntity.setUserEntity(userEntity);
            deviceTokenEntity.setCreatedAt(LocalDateTime.now());
            userEntity.getDeviceTokenEntities().add(deviceTokenEntity);
            userRepository.save(userEntity);
        }
    }

    @Scheduled(cron = "0 0,30 * * * ?")
    public void checkLessonTime() {
        log.info("ActionLog.checkLessonTime.start");

        LocalDateTime now = LocalDateTime.now();
        DayOfWeek today = now.getDayOfWeek();
        LocalTime lessonTime = now.toLocalTime().minusMinutes(30);

        List<GroupEntity> groupEntities = groupRepository.findGroupsByDayAndTime(today.getValue(), lessonTime);

        groupEntities.forEach(groupEntity -> {
            groupEntity.getUserEntities().stream()
                    .filter(userEntity -> userEntity.getUserRoles().getFirst().getRole().getName() == RoleEnum.TEACHER)
                    .findFirst()
                    .ifPresent(teacherEntity -> {
                        AttendanceEntity attendanceEntity = attendanceRepository.findLastByGroupEntityAndUserEntity(groupEntity, teacherEntity);
                        if (attendanceEntity == null || attendanceEntity.getCreatedAt() == null || attendanceEntity.getCreatedAt().isBefore(now.minusMinutes(30))) {
                            sendNotification(teacherEntity);
                        }
                    });
        });
        log.info("ActionLog.checkLessonTime.end");
    }

    private void sendNotification(UserEntity teacherEntity) {
        log.info("ActionLog.checkLessonTime.start teacherEntity {}", teacherEntity.getName());
        Note note = new Note();
        teacherEntity.getDeviceTokenEntities().forEach(deviceTokenEntity -> {
            try {
                firebaseMessagingService.sendNotification(note, deviceTokenEntity.getToken());
            } catch (FirebaseMessagingException e) {
                throw new RuntimeException(e);
            }
        });
        log.info("ActionLog.checkLessonTime.end teacherEntity");
    }


}
