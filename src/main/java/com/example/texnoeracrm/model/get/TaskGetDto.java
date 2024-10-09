package com.example.texnoeracrm.model.get;

import com.example.texnoeracrm.enums.TaskStatusEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskGetDto {
    private Long id;
    private String name;
    private String description;
    private LocalDate deadlineDay;
    private LocalTime deadlineTime;
    @Enumerated(EnumType.STRING)
    private TaskStatusEnum status;
    private LocalDateTime createdAt;
}
