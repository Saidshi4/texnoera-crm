package com.example.texnoeracrm.mapper;

import com.example.texnoeracrm.dao.entity.GroupEntity;
import com.example.texnoeracrm.model.get.GroupGetDto;
import com.example.texnoeracrm.model.set.GroupAssignDto;
import com.example.texnoeracrm.model.set.GroupSetDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface GroupMapper {

    GroupEntity mapToEntity(GroupSetDto groupSetDto);
    @Mapping(target = "userGetDtos", source = "userEntities")
    GroupGetDto mapToDto(GroupEntity groupEntity);

    @Mapping(target = "userGetDtos", source = "userEntities")
    List<GroupGetDto> mapToDtos(List<GroupEntity> groupEntities);

    GroupEntity mapToEntityFromAssignDto(GroupAssignDto groupAssignDto);
}
