package com.example.texnoeracrm.mapper;

import com.example.texnoeracrm.dao.entity.UserEntity;
import com.example.texnoeracrm.model.get.UserGetDto;
import com.example.texnoeracrm.model.set.UserAssignDto;
import com.example.texnoeracrm.model.set.UserSetDto;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity mapToEntity(UserSetDto userSetDto);

    UserGetDto mapToDto(UserEntity userEntity);

    List<UserGetDto> mapToDtos(List<UserEntity> userEntities);

    List<UserEntity> mapToEntities(List<UserGetDto> userGetDtos);
    List<UserEntity> mapAssignDtoToEntities(List<UserAssignDto> userAssignDtos);
    UserEntity mapToEntityFromAssignDto(UserAssignDto userAssignDto);

}
