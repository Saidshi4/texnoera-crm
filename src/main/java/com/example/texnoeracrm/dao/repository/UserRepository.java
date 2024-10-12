package com.example.texnoeracrm.dao.repository;

import com.example.texnoeracrm.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

    @Query("SELECT a.username FROM UserEntity AS a WHERE a.username LIKE :baseUsername% ORDER BY a.username DESC limit 1")
    String findTopByUsernameLikeOrderByUsernameDesc(@Param("baseUsername") String baseUsername);

    @Query("select u from UserEntity u where u.username = :username")
    Optional<UserEntity> findUserEntityByUsername(@Param("username") String username);

    @Query("select u from UserEntity u where u.email = :email")
    Optional<UserEntity> findByEmail(@Param("email") String email);

    @Query(value = "select u from UserEntity as u join users_groups as ug on u.id = ug.userEntity.id where ug.groupEntity.id = :groupId")
    List<UserEntity> findByGroupId(Long groupId);
}
