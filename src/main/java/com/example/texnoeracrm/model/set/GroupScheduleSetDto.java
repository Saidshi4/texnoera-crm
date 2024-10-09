package com.example.texnoeracrm.model.set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupScheduleSetDto {
    private Set<DayOfWeek> daysOfWeek;
    private Map<DayOfWeek, LocalTime> lessonStartTime;
    private Map<DayOfWeek, LocalTime> lessonEndTime;
}
