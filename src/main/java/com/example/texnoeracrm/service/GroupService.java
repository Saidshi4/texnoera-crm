package com.example.texnoeracrm.service;

import com.example.texnoeracrm.dao.entity.GroupEntity;
import com.example.texnoeracrm.dao.entity.UserEntity;
import com.example.texnoeracrm.dao.repository.GroupRepository;
import com.example.texnoeracrm.dao.repository.UserRepository;
import com.example.texnoeracrm.enums.ExceptionEnum;
import com.example.texnoeracrm.exception.NotFoundException;
import com.example.texnoeracrm.mapper.GroupMapper;
import com.example.texnoeracrm.model.get.GroupGetDto;
import com.example.texnoeracrm.model.set.GroupScheduleSetDto;
import com.example.texnoeracrm.model.set.GroupSetDto;
import com.example.texnoeracrm.model.set.UserAssignDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final UserRepository userRepository;
    private GroupEntity findById(Long groupId) {
        log.info("ActionLog.groupFindById.start groupId {}", groupId);
        GroupEntity groupEntity = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(
                        ExceptionEnum.GROUP_NOT_FOUND.name(),
                        String.format(ExceptionEnum.GROUP_NOT_FOUND.getLog(), groupId)
                ));
        log.info("ActionLog.groupFindById.end groupId {}", groupId);
        return groupEntity;
    }

    private UserEntity findUserById(Long userId) {
        log.info("ActionLog.userFindById.start userId {}", userId);
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        ExceptionEnum.USER_NOT_FOUND.name(),
                        String.format(ExceptionEnum.USER_NOT_FOUND.getLog(), userId)
                ));
        log.info("ActionLog.userFindById.end userId {}", userId);
        return userEntity;
    }


    public void createGroup(GroupSetDto groupSetDto) {
        log.info("ActionLog.createGroup.start");
        GroupEntity groupEntity = groupMapper.mapToEntity(groupSetDto);
        groupEntity.setCreatedAt(LocalDateTime.now());
        groupRepository.save(groupEntity);
        log.info("ActionLog.createGroup.end");
    }

    public GroupGetDto getGroup(Long groupId) {
        log.info("ActionLog.getGroup.start groupId {}", groupId);
        GroupEntity groupEntity = findById(groupId);
        GroupGetDto groupGetDto = groupMapper.mapToDto(groupEntity);
        log.info("ActionLog.getGroup.end groupId {}", groupId);
        return groupGetDto;
    }

    public List<GroupGetDto> getAllGroups() {
        log.info("ActionLog.getAllGroups.start");
        List<GroupEntity> groupEntities = groupRepository.findAll();
        List<GroupGetDto> groupGetDtos = groupMapper.mapToDtos(groupEntities);
        log.info("ActionLog.getAllGroups.end");
        return groupGetDtos;
    }

    public void addUsersToGroup(Long groupId, List<UserAssignDto> userAssignDtos) {
        log.info("ActionLog.addUsersToGroup.start");
        GroupEntity groupEntity = findById(groupId);
        for (UserAssignDto userAssignDto : userAssignDtos) {
            UserEntity userEntity = findUserById(userAssignDto.getId());
            if (userEntity.getGroupEntities() == null) {
                userEntity.setGroupEntities(new ArrayList<>());
            }
            if (!userEntity.getGroupEntities().contains(groupEntity)) {
                userEntity.getGroupEntities().add(groupEntity);
                userRepository.save(userEntity);
            }
        }
        if (groupEntity.getUserEntities() == null) {
            groupEntity.setUserEntities(new ArrayList<>());
        }
        List<UserEntity> existingUsers = groupEntity.getUserEntities();
        List<UserEntity> newUsers = userRepository
                .findAllById(userAssignDtos.stream().map(UserAssignDto::getId)
                        .collect(Collectors.toList()));
        for (UserEntity newUser : newUsers) {
            if (!existingUsers.contains(newUser)) {
                groupEntity.getUserEntities().add(newUser);
            }
        }
        groupRepository.save(groupEntity);
        log.info("ActionLog.addUsersToGroup.end");
    }

    public void deleteUsersFromGroup(Long groupId, List<UserAssignDto> userAssignDtos){
        log.info("ActionLog.deleteUsersToGroup.start");
        GroupEntity groupEntity = findById(groupId);
        for (UserAssignDto userAssignDto : userAssignDtos) {
            UserEntity userEntity = findUserById(userAssignDto.getId());
            if (userEntity.getGroupEntities().contains(groupEntity)) {
                userEntity.getGroupEntities().remove(groupEntity);
                userRepository.save(userEntity);
            }
        }
        groupEntity.getUserEntities()
                .removeIf(userEntity -> userAssignDtos.stream()
                        .anyMatch(dto -> dto.getId().equals(userEntity.getId())));
        groupRepository.save(groupEntity);
        log.info("ActionLog.deleteUsersToGroup.end");
    }

    public void setGroupSchedule(Long groupId, GroupScheduleSetDto groupScheduleSetDto) {
        log.info("ActionLog.setGroupSchedule.start");
        GroupEntity groupEntity = findById(groupId);
        groupEntity.setDaysOfWeek(groupScheduleSetDto.getDaysOfWeek());
        groupEntity.setLessonTimes(groupScheduleSetDto.getLessonTime());
        groupRepository.save(groupEntity);
        log.info("ActionLog.setGroupSchedule.end");
    }
//    {
//  "daysOfWeek": ["MONDAY", "TUESDAY", "WEDNESDAY"],
//  "lessonTime": {
//    "MONDAY": "19:00",
//    "TUESDAY": "19:00",
//    "WEDNESDAY": "19:00"
//  }
//}

}
