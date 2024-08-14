package com.example.texnoeracrm.model.get;

import com.example.texnoeracrm.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGetDto {
    private Long id;
    private String name;
    private String surname;
    private String fatherName;
    private LocalDate birthdate;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;
    private RoleEnum role;
    private LocalDateTime created_at;
}
