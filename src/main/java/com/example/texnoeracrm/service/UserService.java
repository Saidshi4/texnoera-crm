package com.example.texnoeracrm.service;

import com.example.texnoeracrm.dao.entity.UserEntity;
import com.example.texnoeracrm.dao.repository.UserRepository;
import com.example.texnoeracrm.enums.ExceptionEnum;
import com.example.texnoeracrm.exception.NotFoundException;
import com.example.texnoeracrm.mapper.UserMapper;
import com.example.texnoeracrm.model.get.UserGetDto;
import com.example.texnoeracrm.model.set.UserSetDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserEntity findById(Long userId) {
        log.info("ActionLog.userFindById.start userId {}", userId);
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        ExceptionEnum.USER_NOT_FOUND.name(),
                        String.format(ExceptionEnum.USER_NOT_FOUND.getLog(), userId)
                ));
        log.info("ActionLog.userFindById.end userId {}", userId);
        return userEntity;
    }

    public void createUser(UserSetDto userSetDto) {
        log.info("ActionLog.createUser.start");
        UserEntity userEntity = userMapper.mapToEntity(userSetDto);
        userEntity.setCreated_at(LocalDateTime.now());
        userRepository.save(userEntity);
        log.info("ActionLog.createUser.end");
    }

    public UserGetDto getUser(Long userId) {
        log.info("ActionLog.getUser.start userId {}", userId);
        UserEntity userEntity = findById(userId);
        UserGetDto userGetDto = userMapper.mapToDto(userEntity);
        log.info("ActionLog.getUser.end userId {}", userId);
        return userGetDto;
    }

    public List<UserGetDto> getAllUser() {
        log.info("ActionLog.getAllUser.start");
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserGetDto> userGetDtos = userMapper.mapToDtos(userEntities);
        log.info("ActionLog.getAllUser.end");
        return userGetDtos;
    }

}
