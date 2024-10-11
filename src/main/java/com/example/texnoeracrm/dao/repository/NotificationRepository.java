package com.example.texnoeracrm.dao.repository;

import com.example.texnoeracrm.dao.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    @Query("select n from notifications n where n.userEntity.id = :userId")
    List<NotificationEntity> findByUserId(Long userId);
}
