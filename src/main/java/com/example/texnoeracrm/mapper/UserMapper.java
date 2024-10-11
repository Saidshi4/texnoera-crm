package com.example.texnoeracrm.mapper;

import com.example.texnoeracrm.dao.entity.UserEntity;
import com.example.texnoeracrm.enums.RoleEnum;
import com.example.texnoeracrm.model.get.GroupUserGetDto;
import com.example.texnoeracrm.model.get.UserGetDto;
import com.example.texnoeracrm.model.set.UserAssignDto;
import com.example.texnoeracrm.model.set.UserSetDto;
import com.example.texnoeracrm.model.set.UserUpdateSetDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity mapToEntity(UserSetDto userSetDto);

    @Mapping(target = "email", source = "newEmail")
    UserEntity mapFromUpdateDtoToEntity(UserUpdateSetDto userUpdateSetDto);

    @Mapping(target = "role", source = "roleEntity.name")
    UserGetDto mapToDto(UserEntity userEntity);

    List<UserGetDto> mapToDtos(List<UserEntity> userEntities);

    @Mapping(target = "role", source = "roleEntity.name")
    GroupUserGetDto mapToGroupUserDto(UserEntity userEntity);

    List<GroupUserGetDto> mapToGroupUserDtos(List<UserEntity> userEntities);

    @Mapping(target = "id", source = "id")
    UserEntity mapToEntityFromAssignDto(UserAssignDto userAssignDto);

}
