package com.Attendance.Attendance.AttendanceRecord.Controller;
import com.Attendance.Attendance.AttendanceRecord.Dto.AttendanceRecordResponse;
import com.Attendance.Attendance.AttendanceRecord.Service.AttendanceRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceRecordController {

    private final AttendanceRecordService attendanceRecordService;

    @PostMapping("/check-in/{employeeId}")
    public ResponseEntity<AttendanceRecordResponse> checkIn(
            @PathVariable Long employeeId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(attendanceRecordService.checkIn(employeeId));
    }

    @PostMapping("/check-out/{employeeId}")
    public ResponseEntity<AttendanceRecordResponse> checkOut(
            @PathVariable Long employeeId) {
        return ResponseEntity.ok(attendanceRecordService.checkOut(employeeId));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<AttendanceRecordResponse>> getByEmployee(
            @PathVariable Long employeeId) {
        return ResponseEntity.ok(
                attendanceRecordService.getEmployeeAttendance(employeeId)
        );
    }

    @GetMapping
    public ResponseEntity<List<AttendanceRecordResponse>> getBetweenDates(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate) {

        return ResponseEntity.ok(
                attendanceRecordService.getAttendanceBetweenDates(startDate, endDate)
        );
    }
}