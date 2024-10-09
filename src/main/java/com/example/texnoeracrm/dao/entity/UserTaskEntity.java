package com.example.texnoeracrm.dao.entity;

import com.example.texnoeracrm.enums.TaskStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity(name = "users_tasks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserTaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    private TaskEntity taskEntity;
    private String answer;
    private LocalDateTime answerTime;
    private Integer grade;
    @Enumerated(EnumType.STRING)
    private TaskStatusEnum status;
}
