package com.example.texnoeracrm.dao.repository;

import com.example.texnoeracrm.dao.entity.AttendanceEntity;
import com.example.texnoeracrm.dao.entity.GroupEntity;
import com.example.texnoeracrm.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long> {

    @Query("SELECT a FROM AttendanceEntity a WHERE a.groupEntity = :group AND a.createdAt BETWEEN :fromDate AND :toDate")
    List<AttendanceEntity> findAttendancesByGroupAndDateRange(@Param("group") GroupEntity groupEntity,
                                                              @Param("fromDate") LocalDateTime fromDate,
                                                              @Param("toDate") LocalDateTime toDate);

    @Query("select a from AttendanceEntity a where a.groupEntity = :groupEntity and a.userEntity = :userEntity order by a.createdAt desc")
    AttendanceEntity findLastByGroupEntityAndUserEntity(@Param("groupEntity") GroupEntity groupEntity, @Param("userEntity") UserEntity userEntity);

    @Query("select a from AttendanceEntity a where a.groupEntity.id = :groupId")
    List<AttendanceEntity> findByGroupId(@Param("groupId") Long groupId);

    @Query("select a from AttendanceEntity a where a.groupEntity.id =:groupId " +
            "and a.expectedAttendanceDate =:expectedAttendanceDate")
    List<AttendanceEntity> findByGroupIdAndExpectedAttendanceDate(@Param("groupId") Long groupId, @Param("expectedAttendanceDate") LocalDate expectedAttendanceDate);

    @Query("select a from AttendanceEntity a where a.groupEntity.id = :groupId and a.userEntity.id = :userId")
    List<AttendanceEntity> findByUserIdAndGroupId(@Param("userId") Long userId, @Param("groupId") Long groupId);
}
