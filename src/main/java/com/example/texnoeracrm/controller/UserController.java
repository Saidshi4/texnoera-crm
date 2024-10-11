package com.example.texnoeracrm.controller;

import com.example.texnoeracrm.model.get.GroupUserGetDto;
import com.example.texnoeracrm.model.get.Note;
import com.example.texnoeracrm.model.get.UserGetDto;
import com.example.texnoeracrm.model.set.UserPasswordSetDto;
import com.example.texnoeracrm.model.set.UserSetDto;
import com.example.texnoeracrm.service.FirebaseMessagingService;
import com.example.texnoeracrm.service.UserService;
import com.example.texnoeracrm.service.auth.JwtService;
import com.google.firebase.messaging.FirebaseMessagingException;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping("/getUser")
    public UserGetDto getUser(HttpServletRequest request) {
        String token = (String) request.getAttribute("token");
        String username = jwtService.extractUsernameAccess(token);
        return userService.getUser(username);
    }

    @GetMapping("/groups/{groupId}")
    public List<GroupUserGetDto> getAllUser(@PathVariable Long groupId) {
        return userService.getUsersByGroupId(groupId);
    }

    @PatchMapping("/changePassword")
    public void changePassword(HttpServletRequest request, @RequestBody UserPasswordSetDto userPasswordSetDto) {
        String token = (String) request.getAttribute("token");
        Long userId = jwtService.extractUserIdFromAccessToken(token, true);
        userService.updatePassword(userId, userPasswordSetDto);
    }

    @DeleteMapping
    public void deleteUser(HttpServletRequest request) {
        String token = (String) request.getAttribute("token");
        Long userId = jwtService.extractUserIdFromAccessToken(token, true);
        userService.deleteUser(userId);
    }



}
