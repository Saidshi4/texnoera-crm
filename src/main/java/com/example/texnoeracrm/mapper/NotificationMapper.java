package com.example.texnoeracrm.mapper;

import com.example.texnoeracrm.dao.entity.NotificationEntity;
import com.example.texnoeracrm.model.get.NotificationGetDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationGetDto mapToDto(NotificationEntity notificationEntity);
    List<NotificationGetDto> mapToDtos(List<NotificationEntity> notificationEntities);
}
