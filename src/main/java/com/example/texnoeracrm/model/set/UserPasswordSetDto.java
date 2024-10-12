package com.example.texnoeracrm.model.set;

import com.example.texnoeracrm.annotation.ValidPassword;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordSetDto {
    private String oldPassword;
    @ValidPassword
    private String newPassword;
}
