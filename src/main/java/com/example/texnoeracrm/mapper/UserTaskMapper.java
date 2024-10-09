package com.example.texnoeracrm.mapper;

import com.example.texnoeracrm.dao.entity.UserTaskEntity;
import com.example.texnoeracrm.model.get.UserTaskGetDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, TaskMapper.class})
public interface UserTaskMapper  {

    @Mapping(target = "taskGetDto", source = "taskEntity")
    UserTaskGetDto mapToDto(UserTaskEntity userTaskEntity);
}
