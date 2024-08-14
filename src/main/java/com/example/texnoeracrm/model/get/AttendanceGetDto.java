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
    private UserGetDto userGetDto;
    private GroupGetDto groupGetDto;
    private AttendanceEnum status;
    private LocalDateTime dateTime;
}
