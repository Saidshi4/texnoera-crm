package com.example.texnoeracrm.model.set;

import com.example.texnoeracrm.enums.GenderEnum;
import com.example.texnoeracrm.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSpecSetDto {
    private String name;
    private String surname;
    private String fatherName;
    private String idCardNo;
    private String personalNo;
    private LocalDate fromBirthdate;
    private LocalDate toBirthdate;
    private GenderEnum gender;
    private String phoneNumber;
    private String email;
    private String username;
    private Boolean isActive;
    private LocalDate fromCreatedAt;
    private LocalDate toCreatedAt;
    private RoleEnum role;
    private Long groupId;
}
