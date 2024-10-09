package com.example.texnoeracrm.mapper;

import com.example.texnoeracrm.dao.entity.RoleEntity;
import com.example.texnoeracrm.model.get.RoleGetDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleGetDto mapToDto(RoleEntity roleEntity);
}
