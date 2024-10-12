package com.example.texnoeracrm.service.specification;

import com.example.texnoeracrm.dao.entity.GroupEntity;
import com.example.texnoeracrm.dao.entity.RoleEntity;
import com.example.texnoeracrm.dao.entity.UserEntity;
import com.example.texnoeracrm.dao.entity.UserGroupEntity;
import com.example.texnoeracrm.enums.GenderEnum;
import com.example.texnoeracrm.enums.RoleEnum;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface UserSpec {

    static Specification<UserEntity> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("name"), name);
    }

    static Specification<UserEntity> hasSurname(String surname) {
        return (root, query, criteriaBuilder) ->
                surname == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("surname"), surname);
    }

    static Specification<UserEntity> hasFatherName(String fatherName) {
        return (root, query, criteriaBuilder) ->
                fatherName == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("fatherName"), fatherName);
    }

    static Specification<UserEntity> hasIdCardNo(String idCardNo) {
        return (root, query, criteriaBuilder) ->
                idCardNo == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("idCardNo"), idCardNo);
    }

    static Specification<UserEntity> hasPersonalNo(String personalNo) {
        return (root, query, criteriaBuilder) ->
                personalNo == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("personalNo"), personalNo);
    }

    static Specification<UserEntity> hasBirthdate(LocalDate fromBirthdate, LocalDate toBirthdate) {
        return (root, query, criteriaBuilder) -> {
            if (fromBirthdate == null && toBirthdate == null) {
                return criteriaBuilder.conjunction();
            } else if (fromBirthdate == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("birthdate"), toBirthdate);
            } else if (toBirthdate == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("birthdate"), fromBirthdate);
            } else {
                return criteriaBuilder.between(root.get("birthdate"), fromBirthdate, toBirthdate);
            }
        };
    }


    static Specification<UserEntity> hasGender(GenderEnum gender) {
        return (root, query, criteriaBuilder) ->
                gender == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("gender"), gender);
    }

    static Specification<UserEntity> hasPhoneNumber(String phoneNumber) {
        return (root, query, criteriaBuilder) ->
                phoneNumber == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("phoneNumber"), phoneNumber);
    }

    static Specification<UserEntity> hasEmail(String email) {
        return (root, query, criteriaBuilder) ->
                email == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("email"), email);
    }

    static Specification<UserEntity> hasUsername(String username) {
        return (root, query, criteriaBuilder) ->
                username == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("username"), username);
    }

    static Specification<UserEntity> isActive(Boolean isActive) {
        return (root, query, criteriaBuilder) ->
                isActive == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("isActive"), isActive);
    }

    static Specification<UserEntity> hasCreatedAt(LocalDateTime fromCreatedAt, LocalDateTime toCreatedAt) {
        return (root, query, criteriaBuilder) -> {
            if (fromCreatedAt == null && toCreatedAt == null) {
                return criteriaBuilder.conjunction();
            } else if (fromCreatedAt == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), toCreatedAt);
            } else if (toCreatedAt == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), fromCreatedAt);
            } else {
                return criteriaBuilder.between(root.get("createdAt"), fromCreatedAt, toCreatedAt);
            }
        };
    }

    //relation spec

    static Specification<UserEntity> hasRole(RoleEntity roleEntity) {
        return (root, query, criteriaBuilder) ->
                roleEntity == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("roleEntity"), roleEntity);
    }

    static Specification<UserEntity> belongsToGroup(Long groupId) {
        return (root, query, criteriaBuilder) -> {
            if (groupId == null) {
                return criteriaBuilder.conjunction();
            }

            Join<UserEntity, UserGroupEntity> userGroupJoin = root.join("userGroupEntities", JoinType.INNER);
            Join<UserGroupEntity, GroupEntity> groupJoin = userGroupJoin.join("groupEntity", JoinType.INNER);

            return criteriaBuilder.equal(groupJoin.get("id"), groupId);
        };
    }



}
