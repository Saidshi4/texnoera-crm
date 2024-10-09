package com.example.texnoeracrm.model.get;

import com.example.texnoeracrm.enums.RoleEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleGetDto {
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleEnum name;
}
