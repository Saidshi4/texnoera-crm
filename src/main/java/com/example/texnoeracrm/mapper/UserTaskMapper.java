package com.example.texnoeracrm.mapper;

import com.example.texnoeracrm.dao.entity.UserTaskEntity;
import com.example.texnoeracrm.model.get.UserTaskGetByTeacherDto;
import com.example.texnoeracrm.model.get.UserTaskGetDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, TaskMapper.class, CommentMapper.class})
public interface UserTaskMapper  {

    @Mapping(target = "taskGetDto", source = "taskEntity")
    @Mapping(target = "commentGetDtos", source = "commentEntities")
    UserTaskGetDto mapToDto(UserTaskEntity userTaskEntity);

    @Mapping(target = "userGetByTaskDto", source = "userEntity")
    UserTaskGetByTeacherDto mapToGetByTeacherDto(UserTaskEntity userTaskEntity);

    List<UserTaskGetByTeacherDto> mapToGetByTeacherDtos(List<UserTaskEntity> userTasks);
}
