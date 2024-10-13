package com.example.texnoeracrm.model.set;

import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskGradeSetDto {
    @Max(100)
    private Integer grade;
}
