package com.example.texnoeracrm.model.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupGetDto {
    private Long id;
    private String name;
    private String lesson;
//    private List<AttendanceGetDto> attendanceGetDtos;
//    private List<TaskGetDto> taskGetDtos;
//    private List<UserGetDto> userGetDtos;

}
