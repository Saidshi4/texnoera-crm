package com.example.texnoeracrm.model.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDto {
    private String username;
    private String password;
    private String deviceToken;
}
