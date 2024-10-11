package com.example.texnoeracrm.model.get;

import com.example.texnoeracrm.enums.TaskStatusEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTaskGetByTeacherDto {
    private Long id;
    private UserGetByTaskDto userGetByTaskDto;
    private String answer;
    private Integer grade;
    private LocalDateTime answerTime;
    @Enumerated(EnumType.STRING)
    private TaskStatusEnum status;
}
