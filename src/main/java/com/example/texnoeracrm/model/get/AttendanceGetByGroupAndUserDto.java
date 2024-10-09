package com.example.texnoeracrm.model.get;

import com.example.texnoeracrm.enums.AttendanceEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceGetByGroupAndUserDto {
    private Long id;
    private AttendanceEnum status;
    private LocalDateTime dateTime;
    private LocalDate expectedAttendanceDate;
}
