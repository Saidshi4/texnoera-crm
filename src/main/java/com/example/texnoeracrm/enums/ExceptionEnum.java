package com.example.texnoeracrm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionEnum {
    USER_NOT_FOUND("ActionLog.findById.error user %d not found"),
    GROUP_NOT_FOUND("ActionLog.findById.error teacher %d not found"),
    TASK_NOT_FOUND("ActionLog.findById.error task %d not found"),
    ATTENDANCE_NOT_FOUND("ActionLog.findById.error attendance %d not found");

    private final String log;
}
