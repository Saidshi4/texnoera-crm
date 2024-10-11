package com.example.texnoeracrm.service;

import com.example.texnoeracrm.dao.entity.AttendanceEntity;
import com.example.texnoeracrm.dao.entity.GroupEntity;
import com.example.texnoeracrm.dao.entity.UserEntity;
import com.example.texnoeracrm.dao.repository.AttendanceRepository;
import com.example.texnoeracrm.dao.repository.GroupRepository;
import com.example.texnoeracrm.dao.repository.UserRepository;
import com.example.texnoeracrm.enums.ExceptionEnum;
import com.example.texnoeracrm.exception.NotFoundException;
import com.example.texnoeracrm.mapper.AttendanceMapper;
import com.example.texnoeracrm.mapper.UserMapper;
import com.example.texnoeracrm.model.get.AttendanceGetByGroupAndUserDto;
import com.example.texnoeracrm.model.get.AttendanceGetDto;
import com.example.texnoeracrm.model.set.AttendanceSetDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private AttendanceEntity findById(Long attendanceId) {
        log.info("ActionLog.attendanceFindById.start attendanceId {}", attendanceId);
        AttendanceEntity attendanceEntity = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new NotFoundException(
                        ExceptionEnum.ATTENDANCE_NOT_FOUND.name(),
                        String.format(ExceptionEnum.ATTENDANCE_NOT_FOUND.getLog(), attendanceId)
                ));
        log.info("ActionLog.attendanceFindById.end attendanceId {}", attendanceId);
        return attendanceEntity;
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

    @Transactional
    public void createAttendances() {
        log.info("ActionLog.createAttendances.start");
        LocalDate now = LocalDate.now();
        List<GroupEntity> groupEntities = groupRepository.findByDaysOfWeek(now.getDayOfWeek());
        groupEntities.forEach(
                groupEntity -> {
                    List<UserEntity> userEntities = userRepository.findByGroupId(groupEntity.getId());
                    userEntities.forEach(
                            userEntity -> {
                                AttendanceEntity attendanceEntity = AttendanceEntity.builder()
                                        .groupEntity(groupEntity)
                                        .userEntity(userEntity)
                                        .expectedAttendanceDate(now)
                                        .build();
                                attendanceRepository.save(attendanceEntity);
                            }
                    );
                }
        );
        log.info("ActionLog.createAttendances.end at {}", now);
    }

    public void enterAttendances(Long groupId, LocalDate expectedAttendanceDate, List<AttendanceSetDto> attendanceSetDtos) {
        log.info("ActionLog.enterAttendances.start groupId {}", groupId);
        LocalDateTime now = LocalDateTime.now();
        List<AttendanceEntity> attendanceEntities = attendanceRepository.findByGroupIdAndExpectedAttendanceDate(groupId, expectedAttendanceDate);
        attendanceSetDtos.forEach(attendanceSetDto -> {
            AttendanceEntity attendanceEntity = attendanceEntities.stream()
                    .filter(a -> a.getUserEntity().getId().equals(attendanceSetDto.getUserAssignDto().getId()))
                    .findFirst()
                    .orElse(null);

            if (attendanceEntity != null) {
                attendanceEntity.setStatus(attendanceSetDto.getStatus());
                attendanceEntity.setCreatedAt(now);
                attendanceRepository.save(attendanceEntity);
            }
        });
        log.info("ActionLog.enterAttendances.end at {}", now);
    }


    public List<LocalDate> getAttendances(Long groupId) {
        log.info("ActionLog.getAttendances.start groupId {}", groupId);
        List<LocalDate> expectedDates = attendanceRepository.findByGroupId(groupId);
        log.info("ActionLog.getAttendances.end groupId {}", groupId);
        return expectedDates;
    }

    public List<AttendanceGetDto> getAttendancesByGroupIdAndDate(Long groupId, LocalDate date) {
        log.info("ActionLog.getAllGroups.start");
        List<AttendanceEntity> attendanceEntities = attendanceRepository.findByGroupIdAndExpectedAttendanceDate(groupId, date);
        List<AttendanceGetDto> attendanceGetDtos = attendanceMapper.mapToDtos(attendanceEntities);
        log.info("ActionLog.getAllGroups.end");
        return attendanceGetDtos;
    }

    public List<AttendanceGetDto> getAttendancesByGroupAndDateRange(Long groupId, LocalDate fromDate, LocalDate toDate) {
        log.info("ActionLog.getAttendancesByGroupAndDateRange.start");
        GroupEntity groupEntity = findGroupById(groupId);
        List<AttendanceEntity> attendanceEntities = attendanceRepository
                .findAttendancesByGroupAndDateRange(groupEntity, fromDate, toDate);
        List<AttendanceGetDto> attendanceGetDtos = attendanceMapper.mapToDtos(attendanceEntities);
        log.info("ActionLog.getAttendancesByGroupAndDateRange.end");
        return attendanceGetDtos;
    }

    public List<AttendanceGetByGroupAndUserDto> getByGroupIdAndUserId(Long groupId, Long userId) {
        log.info("ActionLog.getByGroupIdAndUserId.start");
        List<AttendanceEntity> attendanceEntities = attendanceRepository.findByUserIdAndGroupId(userId, groupId);
        List<AttendanceGetByGroupAndUserDto> attendanceGetDtos = attendanceMapper.mapToGroupAndUserDtos(attendanceEntities);
        log.info("ActionLog.getByGroupIdAndUserId.end");
        return attendanceGetDtos;
    }

}
