package com.example.texnoeracrm.dao.repository;

import com.example.texnoeracrm.dao.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;

public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

    @Query(value = "SELECT g.* FROM groups g " +
            "JOIN group_lesson_times lt ON g.id = lt.group_id " +
            "WHERE lt.day_of_week = :dayOfWeek " +
            "AND lt.lesson_time <= :lessonTime",
            nativeQuery = true)
    List<GroupEntity> findGroupsByDayAndTime(@Param("dayOfWeek") int dayOfWeek, @Param("lessonTime") LocalTime lessonTime);

}
