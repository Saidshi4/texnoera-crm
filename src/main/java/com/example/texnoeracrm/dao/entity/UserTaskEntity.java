package com.example.texnoeracrm.dao.entity;

import com.example.texnoeracrm.enums.TaskStatusEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


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
    @OneToMany(mappedBy = "taskEntity", cascade = CascadeType.REMOVE)
    private List<CommentEntity> commentEntities;
}
