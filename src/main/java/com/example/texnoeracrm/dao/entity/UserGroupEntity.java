package com.example.texnoeracrm.dao.entity;

import jakarta.persistence.*;
import lombok.*;

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
