package com.example.texnoeracrm.model.set;

import com.example.texnoeracrm.enums.TaskStatusEnum;
import com.example.texnoeracrm.model.get.TaskGetDto;
import com.example.texnoeracrm.model.get.UserGetDto;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTaskSetDto {
    private String answer;
}
