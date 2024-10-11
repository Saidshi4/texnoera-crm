package com.example.texnoeracrm.model.set;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordSetDto {
    private String oldPassword;
    private String newPassword;
}
