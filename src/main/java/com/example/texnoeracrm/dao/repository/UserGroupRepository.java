package com.example.texnoeracrm.dao.repository;

import com.example.texnoeracrm.dao.entity.UserGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserGroupRepository extends JpaRepository<UserGroupEntity, Long> {
    @Query("select ug from users_groups ug where ug.groupEntity.id = :groupId")
    List<UserGroupEntity> findByGroupId(Long groupId);

    @Modifying
    @Query("delete users_groups where userEntity.id = :userId and groupEntity.id = :groupId")
    void deleteByGroupIdAndUserId(Long groupId, Long userId);
}
