package com.example.texnoeracrm.controller;

import com.example.texnoeracrm.model.get.AttendanceGetDto;
import com.example.texnoeracrm.model.set.AttendanceSetDto;
import com.example.texnoeracrm.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/attendances")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping
    public void creatAttendance(@RequestBody AttendanceSetDto attendanceSetDto){
        attendanceService.createAttendance(attendanceSetDto);
    }

    @GetMapping("/{attendanceId}")
    public AttendanceGetDto getAttendance(@PathVariable Long attendanceId){
        return attendanceService.getAttendance(attendanceId);
    }

    @GetMapping
    public List<AttendanceGetDto> getAllAttendances(){
        return attendanceService.getAllAttendances();
    }

}
