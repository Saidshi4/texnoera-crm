package com.example.texnoeracrm.model.get;

import com.example.texnoeracrm.enums.TaskStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskGetDto {
    private Long id;
    private String name;
    private String description;
    private TaskStatusEnum status;
    private LocalDateTime created_at;
    private List<UserGetDto> userGetDtos;
}
