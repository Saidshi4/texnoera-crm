package com.example.texnoeracrm.controller;

import com.example.texnoeracrm.model.get.AttendanceGetByGroupAndUserDto;
import com.example.texnoeracrm.model.get.AttendanceGetDto;
import com.example.texnoeracrm.model.set.AttendanceSetDto;
import com.example.texnoeracrm.service.AttendanceService;
import com.example.texnoeracrm.service.auth.JwtService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/attendances")
@RequiredArgsConstructor
@Slf4j
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final JwtService jwtService;

    @GetMapping("/groups/{groupId}")
    public List<LocalDate> getAttendance(@PathVariable Long groupId){
        return attendanceService.getAttendances(groupId);
    }

    @PatchMapping("/groups/{groupId}")
    public void enterAttendance(@PathVariable Long groupId,
                                @Valid @RequestBody List<AttendanceSetDto> attendanceSetDtos){
        log.info("Received JSON: {}", attendanceSetDtos);

        attendanceService.enterAttendances(groupId, attendanceSetDtos);
    }

    @GetMapping("/groups/{groupId}/by-date")
    public List<AttendanceGetDto> getAllAttendances(
            @PathVariable Long groupId,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-M-d") LocalDate date
    ){
        return attendanceService.getAttendancesByGroupIdAndDate(groupId, date);
    }

    @GetMapping("/groups/{groupId}/filter")
    public List<AttendanceGetDto> getAttendancesByGroupAndDateRange(
            @PathVariable Long groupId,
            @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-M-d") LocalDate fromDate,
            @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-M-d") LocalDate toDate) {

        return attendanceService.getAttendancesByGroupAndDateRange(groupId, fromDate, toDate);
    }

    @GetMapping("/users/groups/{groupId}")
    public List<AttendanceGetByGroupAndUserDto> getAttendancesByGroup(HttpServletRequest request, @PathVariable Long groupId) {
        String token = (String) request.getAttribute("token");
        Long userId = jwtService.extractUserIdFromAccessToken(token, true);
        return attendanceService.getByGroupIdAndUserId(groupId, userId);
    }



}
