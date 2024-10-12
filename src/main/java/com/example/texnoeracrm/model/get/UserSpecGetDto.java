package com.example.texnoeracrm.model.get;

import com.example.texnoeracrm.enums.GenderEnum;
import com.example.texnoeracrm.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSpecGetDto {
    private String name;
    private String surname;
    private String fatherName;
    private String idCardNo;
    private String personalNo;
    private LocalDate birthdate;
    private GenderEnum gender;
    private String phoneNumber;
    private String email;
    private String username;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private RoleEnum role;
}
