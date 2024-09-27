package com.example.texnoeracrm.service;

import com.example.texnoeracrm.dao.entity.AttendanceEntity;
import com.example.texnoeracrm.dao.entity.GroupEntity;
import com.example.texnoeracrm.dao.repository.AttendanceRepository;
import com.example.texnoeracrm.dao.repository.GroupRepository;
import com.example.texnoeracrm.enums.ExceptionEnum;
import com.example.texnoeracrm.exception.NotFoundException;
import com.example.texnoeracrm.mapper.AttendanceMapper;
import com.example.texnoeracrm.model.get.AttendanceGetDto;
import com.example.texnoeracrm.model.set.AttendanceSetDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;
    private final GroupRepository groupRepository;

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

    public void createAttendances(Long groupId, List<AttendanceSetDto> attendanceSetDtoList) {
        log.info("ActionLog.createAttendances.start groupId {}", groupId);
        GroupEntity groupEntity = findGroupById(groupId);
        List<AttendanceEntity> attendanceEntities = attendanceMapper.mapToEntities(attendanceSetDtoList);
        attendanceEntities.forEach(attendanceEntity -> {
            attendanceEntity.setGroupEntity(groupEntity);
            attendanceEntity.setCreatedAt(LocalDateTime.now());
            attendanceRepository.save(attendanceEntity);
        });
        log.info("ActionLog.createAttendances.end groupId {}", groupId);
    }

    public AttendanceGetDto getAttendance(Long attendanceId) {
        log.info("ActionLog.getGroup.start attendanceId {}", attendanceId);
        AttendanceEntity attendanceEntity = findById(attendanceId);
        AttendanceGetDto attendanceGetDto = attendanceMapper.mapToDto(attendanceEntity);
        log.info("ActionLog.getGroup.end attendanceId {}", attendanceId);
        return attendanceGetDto;
    }

    public List<AttendanceGetDto> getAllAttendances() {
        log.info("ActionLog.getAllGroups.start");
        List<AttendanceEntity> attendanceEntities = attendanceRepository.findAll();
        List<AttendanceGetDto> attendanceGetDtos = attendanceMapper.mapToDtos(attendanceEntities);
        log.info("ActionLog.getAllGroups.end");
        return attendanceGetDtos;
    }

    public List<AttendanceGetDto> getAttendancesByGroupAndDateRange(Long groupId, LocalDate fromDate, LocalDate toDate) {
        log.info("ActionLog.getAttendancesByGroupAndDateRange.start");
        GroupEntity groupEntity = findGroupById(groupId);
        LocalDateTime startDateTime = fromDate.atStartOfDay();
        LocalDateTime endDateTime = toDate.atTime(LocalTime.MAX);
        List<AttendanceEntity> attendanceEntities = attendanceRepository
                .findAttendancesByGroupAndDateRange(groupEntity, startDateTime, endDateTime);
        List<AttendanceGetDto> attendanceGetDtos = attendanceMapper.mapToDtos(attendanceEntities);
        log.info("ActionLog.getAttendancesByGroupAndDateRange.end");
        return attendanceGetDtos;
    }


}
