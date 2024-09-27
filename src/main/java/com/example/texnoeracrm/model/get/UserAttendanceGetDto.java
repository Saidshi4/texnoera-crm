package com.example.texnoeracrm.model.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAttendanceGetDto {
    private Long id;
    private String name;
    private String surname;
}
