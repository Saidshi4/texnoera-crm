package com.example.texnoeracrm.dao.repository;

import com.example.texnoeracrm.dao.entity.GroupEntity;
import com.example.texnoeracrm.model.get.GroupGetDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

    @Query(value = "SELECT * FROM groups g JOIN group_lesson_start_times lt ON g.id = lt.group_id WHERE lt.day_of_week = :dayOfWeek AND lt.lesson_start_time BETWEEN :startTime AND :endTime",
            nativeQuery = true)
    List<GroupEntity> findGroupsForNotification(@Param("dayOfWeek") int dayOfWeek, @Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime);
    @Query(value = "SELECT * FROM groups g JOIN group_lesson_end_times lt ON g.id = lt.group_id WHERE lt.day_of_week = :dayOfWeek AND lt.lesson_end_time BETWEEN :startTime AND :endTime",
            nativeQuery = true)
    List<GroupEntity> findGroupsByDayAndEndTime(@Param("dayOfWeek") int dayOfWeek, @Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime);

    @Query("SELECT g FROM GroupEntity g JOIN g.daysOfWeek d WHERE d = :dayOfWeek")
    List<GroupEntity> findByDaysOfWeek(DayOfWeek dayOfWeek);

    @Query(value = "SELECT g.* FROM groups g " +
            "JOIN users_groups ug ON g.id = ug.group_id " +
            "JOIN users u ON ug.user_id = u.id " +
            "WHERE u.id = :userId", nativeQuery = true)
    List<GroupEntity> findGroupsByUserId(@Param("userId") Long userId);

}
