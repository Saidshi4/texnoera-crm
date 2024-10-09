package com.example.texnoeracrm.model.get;

import com.example.texnoeracrm.enums.GenderEnum;
import com.example.texnoeracrm.enums.RoleEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupUserGetDto {
    private String name;
    private String surname;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    @Enumerated(EnumType.STRING)
    private RoleEnum role;
}
