package com.example.texnoeracrm.model.get;

import com.example.texnoeracrm.enums.AttendanceEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceGetDto {
    private Long id;
    private UserAttendanceGetDto userAttendanceGetDto;
    private GroupAttendanceGetDto groupAttendanceGetDto;
    private AttendanceEnum status;
    private LocalDateTime dateTime;
}
