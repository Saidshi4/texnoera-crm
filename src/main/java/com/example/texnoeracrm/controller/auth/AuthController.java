package com.example.texnoeracrm.controller.auth;

import com.example.texnoeracrm.model.auth.AuthRequestDto;
import com.example.texnoeracrm.model.auth.AuthenticationDto;
import com.example.texnoeracrm.model.set.UserSetDto;
import com.example.texnoeracrm.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/admin/register/user")
    public void registerUser(@RequestBody UserSetDto userSetDto){
        authService.registerUser(userSetDto);
    }

    @PostMapping("/public/authenticate")
    public AuthenticationDto authenticate(@RequestBody AuthRequestDto authRequestDto){
        return authService.authenticate(authRequestDto);
    }

}