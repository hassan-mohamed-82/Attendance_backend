package com.Attendance.Attendance.AttendanceRecord.Dto;

import com.Attendance.Attendance.AttendanceRecord.Entity.AttendanceRecord;
import com.Attendance.Attendance.common.AttendanceStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Builder
public class AttendanceRecordResponse {

    private Long id;
    private Long employeeId;
    private String employeeName;
    private LocalDate attendanceDate;
    private Instant checkIn;
    private Instant checkOut;
    private AttendanceStatus status;
    private String notes;

    public static AttendanceRecordResponse from(AttendanceRecord record) {
        return AttendanceRecordResponse.builder()
                .id(record.getId())
                .employeeId(record.getEmployee().getId())
                .employeeName(record.getEmployee().getFullName())
                .attendanceDate(record.getAttendanceDate())
                .checkIn(record.getCheckIn())
                .checkOut(record.getCheckOut())
                .status(record.getStatus())
                .notes(record.getNotes())
                .build();
    }
}