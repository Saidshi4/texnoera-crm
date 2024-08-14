package com.example.texnoeracrm.model.set;

import com.example.texnoeracrm.enums.RoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSetDto {
    private String name;
    private String surname;
    private String fatherName;
    private String idCardNo;
    private String personalNo;
    private LocalDate birthdate;
    private String phoneNumber;
    private String email;
    private Long diplomaNo;
    private Double averageScore;
    private String username;
    private String password;
    @Schema(description = "ADMIN, STUDENT, TEACHER, MENTOR")
    private RoleEnum role;
}
