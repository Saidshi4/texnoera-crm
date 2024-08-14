package com.example.texnoeracrm.controller;

import com.example.texnoeracrm.model.get.UserGetDto;
import com.example.texnoeracrm.model.set.UserSetDto;
import com.example.texnoeracrm.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public void creatUser(@RequestBody UserSetDto userSetDto){
        userService.createUser(userSetDto);
    }

    @GetMapping("/{userId}")
    public UserGetDto getUser(@PathVariable Long userId){
        return userService.getUser(userId);
    }

    @GetMapping
    public List<UserGetDto> getAllUser(){
        return userService.getAllUser();
    }

}
