package com.example.texnoeracrm.dao.repository;

import com.example.texnoeracrm.dao.entity.UserTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserTaskRepository extends JpaRepository<UserTaskEntity, Long> {

    @Query("select ut from users_tasks ut where ut.taskEntity.id = :taskId and ut.userEntity.id = :userId")
    UserTaskEntity findByTaskIdAndUserId(Long taskId, Long userId);

    @Query("select ut from users_tasks ut where ut.taskEntity.id = :taskId")
    List<UserTaskEntity> findByTaskId(Long taskId);
}
