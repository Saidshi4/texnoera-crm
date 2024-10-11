package com.example.texnoeracrm.mapper;

import com.example.texnoeracrm.dao.entity.CommentEntity;
import com.example.texnoeracrm.model.get.CommentGetDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentGetDto mapToDto(CommentEntity commentEntity);
    List<CommentGetDto> mapToDtos(List<CommentEntity> commentEntityList);
}
