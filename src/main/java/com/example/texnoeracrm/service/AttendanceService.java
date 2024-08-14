package com.example.texnoeracrm.service;

import com.example.texnoeracrm.dao.entity.AttendanceEntity;
import com.example.texnoeracrm.dao.repository.AttendanceRepository;
import com.example.texnoeracrm.enums.ExceptionEnum;
import com.example.texnoeracrm.exception.NotFoundException;
import com.example.texnoeracrm.mapper.AttendanceMapper;
import com.example.texnoeracrm.model.get.AttendanceGetDto;
import com.example.texnoeracrm.model.set.AttendanceSetDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;

    public AttendanceEntity findById(Long attendanceId){
        log.info("ActionLog.attendanceFindById.start attendanceId {}", attendanceId);
        AttendanceEntity attendanceEntity = attendanceRepository.findById(attendanceId)
                .orElseThrow(()-> new NotFoundException(
                        ExceptionEnum.ATTENDANCE_NOT_FOUND.name(),
                        String.format(ExceptionEnum.ATTENDANCE_NOT_FOUND.getLog(), attendanceId)
                ));
        log.info("ActionLog.attendanceFindById.end attendanceId {}", attendanceId);
        return attendanceEntity;
    }

    public void createAttendance(AttendanceSetDto attendanceSetDto){
        log.info("ActionLog.createAttendance.start");
        AttendanceEntity attendanceEntity = attendanceMapper.mapToEntity(attendanceSetDto);
        attendanceEntity.setDateTime(LocalDateTime.now());
        attendanceRepository.save(attendanceEntity);
        log.info("ActionLog.createGroup.end");
    }

    public AttendanceGetDto getAttendance(Long attendanceId){
        log.info("ActionLog.getGroup.start attendanceId {}", attendanceId);
        AttendanceEntity attendanceEntity = findById(attendanceId);
        AttendanceGetDto attendanceGetDto = attendanceMapper.mapToDto(attendanceEntity);
        log.info("ActionLog.getGroup.end attendanceId {}", attendanceId);
        return attendanceGetDto;
    }

    public List<AttendanceGetDto> getAllAttendances(){
        log.info("ActionLog.getAllGroups.start");
        List<AttendanceEntity> attendanceEntities = attendanceRepository.findAll();
        List<AttendanceGetDto> attendanceGetDtos = attendanceMapper.mapToDtos(attendanceEntities);
        log.info("ActionLog.getAllGroups.end");
        return attendanceGetDtos;
    }


}
