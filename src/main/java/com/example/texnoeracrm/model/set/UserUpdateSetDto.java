package com.example.texnoeracrm.model.set;

import com.example.texnoeracrm.enums.GenderEnum;
import com.example.texnoeracrm.enums.RoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateSetDto {
    private String name;
    private String surname;
    private String fatherName;
    private String idCardNo;
    private String personalNo;
    private LocalDate birthdate;
    private String phoneNumber;
    private String oldEmail;
    private String newEmail;
    @Schema(description = "ADMIN, STUDENT, TEACHER, MENTOR")
    private RoleEnum role;
    private GenderEnum gender;

}
