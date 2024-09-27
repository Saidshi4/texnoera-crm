package com.example.texnoeracrm.dao.repository;

import com.example.texnoeracrm.dao.entity.RoleEntity;
import com.example.texnoeracrm.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    @Query(value = "select r from roles as r where r.name = :role")
    RoleEntity findByName(@Param("role") RoleEnum role);
}
