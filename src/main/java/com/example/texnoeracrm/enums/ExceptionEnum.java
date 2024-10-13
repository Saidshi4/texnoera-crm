package com.example.texnoeracrm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionEnum {
    USER_NOT_FOUND("ActionLog.findById.error user %d not found"),
    USER_NOT_FOUND_BY_USERNAME("ActionLog.findByUsername.error user %s not found"),
    USER_NOT_FOUND_BY_EMAIL("ActionLog.findByEmail.error by email %s"),
    USER_NOT_ACTIVE("ActionLog.findById.error user %s not active"),
    EMAIL_ALREADY_EXISTS("ActionLog.findByEmail.error email %s already exists"),
    GROUP_NOT_FOUND("ActionLog.findById.error teacher %d not found"),
    TASK_NOT_FOUND("ActionLog.findById.error task %d not found"),
    ATTENDANCE_NOT_FOUND("ActionLog.findById.error attendance %d not found"),
    PASSWORD_DONT_MATCH("ActionLog.authenticate.error"),
    INVALID_TOKEN("ActionLog.authenticate.error.token invalid"),
    INCORRECT_PASSWORD("ActionLog.authenticate.error old password incorrect"),
    USER_TASK_NOT_FOUND("ActionLog.findById.error userTask %d not found");

    private final String log;
}
