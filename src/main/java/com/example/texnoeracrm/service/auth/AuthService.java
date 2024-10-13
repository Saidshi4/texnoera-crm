package com.example.texnoeracrm.service.auth;

import com.example.texnoeracrm.dao.entity.DeviceTokenEntity;
import com.example.texnoeracrm.dao.entity.RoleEntity;
import com.example.texnoeracrm.dao.entity.UserEntity;
import com.example.texnoeracrm.dao.repository.DeviceTokenRepository;
import com.example.texnoeracrm.dao.repository.RoleRepository;
import com.example.texnoeracrm.dao.repository.UserRepository;
import com.example.texnoeracrm.enums.ExceptionEnum;
import com.example.texnoeracrm.exception.AlreadyExistException;
import com.example.texnoeracrm.exception.NotFoundException;
import com.example.texnoeracrm.exception.UserNotActiveException;
import com.example.texnoeracrm.exception.UserNotAuthorizedException;
import com.example.texnoeracrm.mapper.UserMapper;
import com.example.texnoeracrm.model.auth.AuthRequestDto;
import com.example.texnoeracrm.model.auth.AuthenticationDto;
import com.example.texnoeracrm.model.get.UserGetDto;
import com.example.texnoeracrm.model.set.UserSetDto;
import com.example.texnoeracrm.model.set.UserUpdateSetDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final DeviceTokenRepository deviceTokenRepository;

    public UserGetDto registerUser(UserSetDto userSetDto) {
        log.info("ActionLog.registerUser.start");
        UserEntity userByEmail = userRepository.findByEmail(userSetDto.getEmail()).orElse(null);

        if (userByEmail != null) {
            throw new AlreadyExistException(ExceptionEnum.EMAIL_ALREADY_EXISTS.name(),
                    String.format(ExceptionEnum.EMAIL_ALREADY_EXISTS.getLog(), userSetDto.getEmail())
            );
        }
        RoleEntity roleEntity = roleRepository.findByName(userSetDto.getRole());
        UserEntity userEntity = userMapper.mapToEntity(userSetDto);
        userEntity.setUsername(generateUsername(userSetDto.getName(), userSetDto.getSurname(), userSetDto.getRole().name()));
        userEntity.setPassword(passwordEncoder.encode(generatePassword(userEntity)));
        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity.setRoleEntity(roleEntity);
        UserGetDto userGetDto = userMapper.mapToDto(userEntity);
        userRepository.save(userEntity);
        log.info("ActionLog.registerUser.end");
        return userGetDto;
    }

    public UserGetDto updateUser(UserUpdateSetDto userUpdateSetDto) {
        log.info("ActionLog.updateUser.start");
        UserEntity userByEmail = userRepository.findByEmail(userUpdateSetDto.getOldEmail()).orElse(null);
        if (userByEmail == null) {
            throw new NotFoundException(ExceptionEnum.USER_NOT_FOUND_BY_EMAIL.name(),
                    String.format(ExceptionEnum.USER_NOT_FOUND_BY_EMAIL.getLog(), userUpdateSetDto.getOldEmail())
            );
        }
        RoleEntity roleEntity = roleRepository.findByName(userUpdateSetDto.getRole());
        userByEmail.setName(userUpdateSetDto.getName());
        userByEmail.setSurname(userUpdateSetDto.getSurname());
        userByEmail.setFatherName(userUpdateSetDto.getFatherName());
        userByEmail.setIdCardNo(userUpdateSetDto.getIdCardNo());
        userByEmail.setPersonalNo(userUpdateSetDto.getPersonalNo());
        userByEmail.setBirthdate(userUpdateSetDto.getBirthdate());
        userByEmail.setPhoneNumber(userUpdateSetDto.getPhoneNumber());
        userByEmail.setEmail(userUpdateSetDto.getNewEmail());
        userByEmail.setRoleEntity(roleEntity);
        userByEmail.setGender(userUpdateSetDto.getGender());
        userByEmail.setUsername(generateUsername(userUpdateSetDto.getName(),
                userUpdateSetDto.getSurname(), userUpdateSetDto.getRole().name()));
        userByEmail.setPassword(passwordEncoder.encode(generatePassword(userByEmail)));
        UserGetDto userGetDto = userMapper.mapToDto(userByEmail);
        userRepository.save(userByEmail);
        log.info("ActionLog.updateUser.end");
        return userGetDto;
    }


    private String generateUsername(String name, String surname, String role) {
        log.info("ActionLog.generateUsername.start");

        String baseUsername = name.toLowerCase().charAt(0) + surname.toLowerCase() + "." + role.toLowerCase().charAt(0);
        String topUsername = userRepository.findTopByUsernameLikeOrderByUsernameDesc(baseUsername + "%");

        if (topUsername == null) {
            log.info("ActionLog.generateUsername.end username {}", baseUsername);
            return baseUsername;
        }
        String numberPart = "";
        if (topUsername.matches(".*\\d+$")) {
            numberPart = topUsername.replaceAll("\\D+", "");
        }
        int counter = 1;
        if (!numberPart.isEmpty()) {
            counter = Integer.parseInt(numberPart) + 1;
        }
        String newUsername = baseUsername + counter;
        log.info("ActionLog.generateUsername.end username {}", newUsername);
        return newUsername;
    }

    private String generatePassword(UserEntity userEntity) {
        log.info("ActionLog.generatePassword.start");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        String birthYear = userEntity.getBirthdate().format(formatter);
        log.info("ActionLog.generatePassword.end");
        return userEntity.getName() + birthYear;
    }

    public AuthenticationDto authenticate(AuthRequestDto authRequestDto) {
        log.info("ActionLog.authenticate.start for user: {}", authRequestDto.getUsername());
        UserEntity userEntity = findUserByUsername(authRequestDto.getUsername());
        if (!userEntity.getIsActive()){
            throw new UserNotActiveException(ExceptionEnum.USER_NOT_ACTIVE.name(),
                    String.format(ExceptionEnum.USER_NOT_ACTIVE.getLog(), authRequestDto.getUsername()));
        }
        verifyPassword(authRequestDto.getPassword(), userEntity.getPassword());
        authenticateUser(authRequestDto.getUsername(), authRequestDto.getPassword());
        registerDeviceToken(authRequestDto.getDeviceToken(), userEntity);
        log.info("ActionLog.authenticate.end for user: {}", authRequestDto.getUsername());
        return createAuthenticationTokens(userEntity);
    }

    private void registerDeviceToken(String deviceToken, UserEntity userEntity) {
        log.info("ActionLog.registerDeviceToken.start");

        UserEntity userByDevice = userRepository.findByDeviceToken(deviceToken).orElse(null);
        DeviceTokenEntity deviceTokenEntity = deviceTokenRepository.findByToken(deviceToken);

        if (userByDevice != null) {
            if (userByDevice.equals(userEntity)) {
                log.info("ActionLog.registerDeviceToken.end user already registered");
            } else {
                if (deviceTokenEntity != null) {
                    userByDevice.getDeviceTokenEntities().remove(deviceTokenEntity);
                    userRepository.save(userByDevice);
                }
                userEntity.getDeviceTokenEntities().add(deviceTokenEntity);
                userRepository.save(userEntity);
            }
        } else {
            if (deviceTokenEntity != null) {
                userEntity.getDeviceTokenEntities().add(deviceTokenEntity);
                userRepository.save(userEntity);
            }
        }

        log.info("ActionLog.registerDeviceToken.end");
    }



    private UserEntity findUserByUsername(String username) {
        log.info("ActionLog.findUserByUsername.start for username: {}", username);
        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new NotFoundException(
                        ExceptionEnum.USER_NOT_FOUND_BY_USERNAME.name(),
                        String.format(ExceptionEnum.USER_NOT_FOUND_BY_USERNAME.getLog(), username)
                ));
        log.info("ActionLog.findUserByUsername.success for username: {}", username);
        return userEntity;
    }

    private void verifyPassword(String rawPassword, String encodedPassword) {
        log.info("ActionLog.verifyPassword.start");
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            log.warn("ActionLog.verifyPassword.failed: Passwords don't match");
            throw new UserNotAuthorizedException(
                    ExceptionEnum.PASSWORD_DONT_MATCH.name(),
                    ExceptionEnum.PASSWORD_DONT_MATCH.getLog()
            );
        }
        log.info("ActionLog.verifyPassword.end");
    }

    private void authenticateUser(String username, String password) {
        log.info("ActionLog.authenticateUser.start for username: {}", username);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        log.info("ActionLog.authenticateUser.end for username: {}", username);
    }

    private AuthenticationDto createAuthenticationTokens(UserEntity userEntity) {
        log.info("ActionLog.createAuthenticationTokens.start for userId: {}", userEntity.getId());
        var accessToken = jwtService.generateAccessToken(userEntity);
        var refreshToken = jwtService.generateRefreshToken(userEntity);
        log.info("ActionLog.createAuthenticationTokens.end for userId: {}", userEntity.getId());
        return new AuthenticationDto(accessToken, refreshToken);
    }

}
