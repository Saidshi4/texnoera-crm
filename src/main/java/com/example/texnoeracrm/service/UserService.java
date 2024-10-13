package com.example.texnoeracrm.service;

import com.example.texnoeracrm.dao.entity.UserEntity;
import com.example.texnoeracrm.dao.repository.RoleRepository;
import com.example.texnoeracrm.dao.repository.UserRepository;
import com.example.texnoeracrm.enums.ExceptionEnum;
import com.example.texnoeracrm.exception.IncorrectPasswordException;
import com.example.texnoeracrm.exception.NotFoundException;
import com.example.texnoeracrm.mapper.UserMapper;
import com.example.texnoeracrm.model.get.GroupUserGetDto;
import com.example.texnoeracrm.model.get.UserGetDto;
import com.example.texnoeracrm.model.get.UserSpecGetDto;
import com.example.texnoeracrm.model.set.UserPasswordSetDto;
import com.example.texnoeracrm.model.set.UserSpecSetDto;
import com.example.texnoeracrm.service.specification.UserSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    private UserEntity findById(Long userId) {
        log.info("ActionLog.userFindById.start userId {}", userId);
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        ExceptionEnum.USER_NOT_FOUND.name(),
                        String.format(ExceptionEnum.USER_NOT_FOUND.getLog(), userId)
                ));
        log.info("ActionLog.userFindById.end userId {}", userId);
        return userEntity;
    }

    private UserEntity findByUsername(String username) {
        log.info("ActionLog.userFindByUsername.start username {}", username);
        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new NotFoundException(
                        ExceptionEnum.USER_NOT_FOUND_BY_USERNAME.name(),
                        String.format(ExceptionEnum.USER_NOT_FOUND_BY_USERNAME.getLog(), username)
                ));
        log.info("ActionLog.userFindByUsername.end username {}", username);
        return userEntity;
    }

    public UserGetDto getUser(String username) {
        log.info("ActionLog.getUser.start username {}", username);
        UserEntity userEntity = findByUsername(username);
        UserGetDto userGetDto = userMapper.mapToDto(userEntity);
        log.info("ActionLog.getUser.end username {}", username);
        return userGetDto;
    }

    public List<GroupUserGetDto> getUsersByGroupId(Long groupId) {
        log.info("ActionLog.getUsersByGroupId.start groupId {}", groupId);
        List<UserEntity> userEntities = userRepository.findByGroupId(groupId);
        List<GroupUserGetDto> groupUserGetDtos = userMapper.mapToGroupUserDtos(userEntities);
        log.info("ActionLog.getUsersByGroupId.end groupId {}", groupId);
        return groupUserGetDtos;
    }

    public void updatePassword(Long userId, UserPasswordSetDto userPasswordSetDto) {
        log.info("ActionLog.updatePassword.start userId {}", userId);
        UserEntity userEntity = findById(userId);
        if (!passwordEncoder.matches(userPasswordSetDto.getOldPassword(), userEntity.getPassword())) {
            throw new IncorrectPasswordException(
                    ExceptionEnum.INCORRECT_PASSWORD.name(), ExceptionEnum.INCORRECT_PASSWORD.getLog()
            );
        }
        String encodedPassword = passwordEncoder.encode(userPasswordSetDto.getNewPassword());
        userEntity.setPassword(encodedPassword);
        userRepository.save(userEntity);
        log.info("ActionLog.updatePassword.end userId {}", userId);
    }

    public void deleteUser(Long userId) {
        log.info("ActionLog.deleteUser.start");
        UserEntity userEntity = findById(userId);
        userEntity.setIsActive(false);
        userRepository.save(userEntity);
        log.info("ActionLog.deleteUser.end userId {}", userId);
    }

    public List<UserSpecGetDto> findUsers(UserSpecSetDto userSpecSetDto) {
        log.info("ActionLog.findUsers.start");

        Specification<UserEntity> spec = UserSpec.fromUserSpecSetDto(userSpecSetDto, roleRepository.findByName(userSpecSetDto.getRole()));

        List<UserEntity> users = userRepository.findAll(spec, Sort.by("name"));
        List<UserSpecGetDto> userSpecGetDtos = userMapper.mapToSpecGetDtos(users);

        log.info("ActionLog.findUsers.end");
        return userSpecGetDtos;
    }




}
