package com.example.texnoeracrm.controller;

import com.example.texnoeracrm.model.get.Note;
import com.example.texnoeracrm.model.get.UserGetDto;
import com.example.texnoeracrm.model.set.UserSetDto;
import com.example.texnoeracrm.service.FirebaseMessagingService;
import com.example.texnoeracrm.service.UserService;
import com.google.firebase.messaging.FirebaseMessagingException;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public UserGetDto getUser(@PathVariable Long userId){
        return userService.getUser(userId);
    }

    @GetMapping
    public List<UserGetDto> getAllUser(){
        return userService.getAllUser();
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }



}
