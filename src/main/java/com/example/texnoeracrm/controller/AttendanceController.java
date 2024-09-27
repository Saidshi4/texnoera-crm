package com.example.texnoeracrm.controller;

import com.example.texnoeracrm.model.get.AttendanceGetDto;
import com.example.texnoeracrm.model.set.AttendanceSetDto;
import com.example.texnoeracrm.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/attendances")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/groups/{groupId}")
    public void creatAttendances(@PathVariable Long groupId, @RequestBody List<AttendanceSetDto> attendanceSetDtoList){
        attendanceService.createAttendances(groupId, attendanceSetDtoList);
    }

    @GetMapping("/{attendanceId}")
    public AttendanceGetDto getAttendance(@PathVariable Long attendanceId){
        return attendanceService.getAttendance(attendanceId);
    }

    @GetMapping
    public List<AttendanceGetDto> getAllAttendances(){
        return attendanceService.getAllAttendances();
    }

    @GetMapping("/groups/{groupId}")
    public List<AttendanceGetDto> getAttendancesByGroupAndDateRange(
            @PathVariable Long groupId,
            @RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-M-d") LocalDate fromDate,
            @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-M-d") LocalDate toDate) {

        return attendanceService.getAttendancesByGroupAndDateRange(groupId, fromDate, toDate);
    }



}
