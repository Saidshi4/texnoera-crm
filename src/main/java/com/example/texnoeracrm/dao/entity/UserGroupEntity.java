package com.example.texnoeracrm.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity(name = "users_groups")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    private GroupEntity groupEntity;
    private Long diplomaNo;
    private Double averageScore;
}
