package com.example.texnoeracrm.controller;

import com.example.texnoeracrm.model.get.GroupByUserIdGetDto;
import com.example.texnoeracrm.model.get.GroupGetDto;
import com.example.texnoeracrm.model.get.UserGetDto;
import com.example.texnoeracrm.model.set.GroupScheduleSetDto;
import com.example.texnoeracrm.model.set.GroupSetDto;
import com.example.texnoeracrm.model.set.UserAssignDto;
import com.example.texnoeracrm.service.GroupService;
import com.example.texnoeracrm.service.auth.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    private final JwtService jwtService;

    @PostMapping
    public void creatGroup(@RequestBody GroupSetDto groupSetDto){
        groupService.createGroup(groupSetDto);
    }
    
    @GetMapping
    public List<GroupGetDto> getAllGroups(){
        return groupService.getAllGroups();
    }

    @PatchMapping("/{groupId}/add-users")
    public void addUsersToGroup(@PathVariable Long groupId, @RequestBody List<UserAssignDto> userAssignDtos){
        groupService.addUsersToGroup(groupId, userAssignDtos);
    }

    @DeleteMapping("{groupId}/delete-users")
    public void deleteUsersFromGroup(@PathVariable Long groupId, @RequestBody List<UserAssignDto> userAssignDtos){
        groupService.deleteUsersFromGroup(groupId, userAssignDtos);
    }

    @PatchMapping("/{groupId}/schedule")
    public void setGroupSchedule(@PathVariable Long groupId, @RequestBody GroupScheduleSetDto groupScheduleSetDto){
        groupService.setGroupSchedule(groupId, groupScheduleSetDto);
    }

    @GetMapping("/get-groups")
    public List<GroupByUserIdGetDto> getGroupsByUserId(HttpServletRequest request) {
        String token = (String) request.getAttribute("token");
        Long userId = jwtService.extractUserIdFromAccessToken(token, true);
        return groupService.getGroupByUserId(userId);
    }
}