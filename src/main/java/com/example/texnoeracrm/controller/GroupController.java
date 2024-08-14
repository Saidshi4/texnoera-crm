package com.example.texnoeracrm.controller;

import com.example.texnoeracrm.model.get.GroupGetDto;
import com.example.texnoeracrm.model.get.UserGetDto;
import com.example.texnoeracrm.model.set.GroupSetDto;
import com.example.texnoeracrm.model.set.UserAssignDto;
import com.example.texnoeracrm.service.GroupService;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping
    public void creatGroup(@RequestBody GroupSetDto groupSetDto){
        groupService.createGroup(groupSetDto);
    }

    @GetMapping("/{groupId}")
    public GroupGetDto getUser(@PathVariable Long groupId){
        return groupService.getGroup(groupId);
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

}