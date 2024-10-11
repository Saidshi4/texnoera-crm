package com.example.texnoeracrm.dao.repository;

import com.example.texnoeracrm.dao.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
