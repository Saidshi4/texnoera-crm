package com.example.texnoeracrm.controller.auth;

import com.example.texnoeracrm.model.auth.AuthRequestDto;
import com.example.texnoeracrm.model.auth.AuthenticationDto;
import com.example.texnoeracrm.model.get.UserGetDto;
import com.example.texnoeracrm.model.set.UserSetDto;
import com.example.texnoeracrm.model.set.UserUpdateSetDto;
import com.example.texnoeracrm.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/admin/register/user")
    public UserGetDto registerUser(@RequestBody UserSetDto userSetDto){
        return authService.registerUser(userSetDto);
    }

    @PostMapping("/public/authenticate")
    public AuthenticationDto authenticate(@RequestBody AuthRequestDto authRequestDto){
        return authService.authenticate(authRequestDto);
    }

    @PutMapping("/admin")
    public UserGetDto updateUser(@RequestBody UserUpdateSetDto userUpdateSetDto){
        return authService.updateUser(userUpdateSetDto);
    }

}