package com.example.texnoeracrm.model.set;

import com.example.texnoeracrm.enums.AttendanceEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceSetDto {
    private Long id;
    private AttendanceEnum status;

}
