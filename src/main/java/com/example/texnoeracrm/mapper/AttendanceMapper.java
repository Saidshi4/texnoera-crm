package com.example.texnoeracrm.mapper;

import com.example.texnoeracrm.dao.entity.AttendanceEntity;
import com.example.texnoeracrm.model.get.AttendanceGetDto;
import com.example.texnoeracrm.model.set.AttendanceSetDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, GroupMapper.class})
public interface AttendanceMapper {

    @Mapping(source = "userAssignDto", target = "userEntity")
    @Mapping(source = "groupAssignDto", target = "groupEntity")
    AttendanceEntity mapToEntity(AttendanceSetDto attendanceSetDto);

    @Mapping(target = "userGetDto", source = "userEntity")
    @Mapping(target = "groupGetDto", source = "groupEntity")
    AttendanceGetDto mapToDto(AttendanceEntity attendanceEntity);

    @Mapping(target = "userAssignDto", source = "userEntity")
    @Mapping(target = "groupAssignDto", source = "groupEntity")
    List<AttendanceGetDto> mapToDtos(List<AttendanceEntity> attendanceEntities);
}
