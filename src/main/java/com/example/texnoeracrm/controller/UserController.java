package com.example.texnoeracrm.controller;

import com.example.texnoeracrm.model.get.GroupUserGetDto;
import com.example.texnoeracrm.model.get.Note;
import com.example.texnoeracrm.model.get.UserGetDto;
import com.example.texnoeracrm.model.get.UserSpecGetDto;
import com.example.texnoeracrm.model.set.UserPasswordSetDto;
import com.example.texnoeracrm.model.set.UserSpecSetDto;
import com.example.texnoeracrm.service.UserService;
import com.example.texnoeracrm.service.auth.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping("/get-user")
    public UserGetDto getUser(HttpServletRequest request) {
        String token = (String) request.getAttribute("token");
        String username = jwtService.extractUsernameAccess(token);
        return userService.getUser(username);
    }

    @GetMapping("/groups/{groupId}")
    public List<GroupUserGetDto> getAllUser(@PathVariable Long groupId) {
        return userService.getUsersByGroupId(groupId);
    }

    @PatchMapping("/change-password")
    public void changePassword(HttpServletRequest request,@Validated @RequestBody UserPasswordSetDto userPasswordSetDto) {
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

    @GetMapping("/find-users")
    public List<UserSpecGetDto> findUsers(@RequestBody UserSpecSetDto userSpecSetDto) {
        return userService.findUsers(userSpecSetDto);
    }



}
