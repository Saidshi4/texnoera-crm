package com.example.texnoeracrm.mapper;

import com.example.texnoeracrm.dao.entity.TaskEntity;
import com.example.texnoeracrm.model.get.TaskGetDto;
import com.example.texnoeracrm.model.set.TaskSetDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskEntity mapToEntity(TaskSetDto taskSetDto);

    TaskGetDto mapToDto(TaskEntity taskEntity);

    List<TaskGetDto> mapToDtos(List<TaskEntity> taskEntities);
}
