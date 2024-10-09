package com.example.texnoeracrm.dao.repository;

import com.example.texnoeracrm.dao.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    @Query("select taskEntity from users_tasks where userEntity.id = :userId")
    List<TaskEntity> findByUserId(Long userId);

    @Query("select t from TaskEntity t join users_tasks ut on ut.taskEntity.id = t.id where t.deadlineDay = :date and t.deadlineTime between :start and :end")
    List<TaskEntity> findByDeadline(LocalDate date, LocalTime start, LocalTime end);

    @Query("select t from TaskEntity t join users_tasks ut on t.id = ut.taskEntity.id where ut.userEntity.id = :userId and t.groupEntity.id = :groupId")
    List<TaskEntity> findByUserIdAndGroupId(Long userId, Long groupId);
}
