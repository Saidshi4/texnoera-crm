package com.example.texnoeracrm.model.set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskSetDto {
    private String name;
    private String description;
    private LocalDate date;
    private LocalTime time;
}
