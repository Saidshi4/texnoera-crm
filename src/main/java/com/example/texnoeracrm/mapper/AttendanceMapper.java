package com.example.texnoeracrm.mapper;

import com.example.texnoeracrm.dao.entity.AttendanceEntity;
import com.example.texnoeracrm.model.get.AttendanceGetByGroupAndUserDto;
import com.example.texnoeracrm.model.get.AttendanceGetDto;
import com.example.texnoeracrm.model.set.AttendanceSetDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, GroupMapper.class})
public interface AttendanceMapper {

    @Mapping(source = "userAssignDto", target = "userEntity")
    AttendanceEntity mapToEntity(AttendanceSetDto attendanceSetDto);

    List<AttendanceEntity> mapToEntities(List<AttendanceSetDto> attendanceSetDtos);

    @Mapping(target = "userAttendanceGetDto", source = "userEntity")
    @Mapping(target = "groupAttendanceGetDto", source = "groupEntity")
    AttendanceGetDto mapToDto(AttendanceEntity attendanceEntity);

    List<AttendanceGetDto> mapToDtos(List<AttendanceEntity> attendanceEntities);

    List<AttendanceGetByGroupAndUserDto> mapToGroupAndUserDtos(List<AttendanceEntity> attendanceEntities);
}
