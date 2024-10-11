package com.example.texnoeracrm.dao.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Entity(name = "notifications")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    @ManyToOne
    private UserEntity userEntity;
}
