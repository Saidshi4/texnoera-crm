package com.example.texnoeracrm.dao.repository;

import com.example.texnoeracrm.dao.entity.NotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    Page<NotificationEntity> findByUserEntityId(Long userId, Pageable pageable);
}
