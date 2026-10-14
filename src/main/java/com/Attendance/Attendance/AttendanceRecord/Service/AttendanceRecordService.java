package com.Attendance.Attendance.AttendanceRecord.Service;

import com.Attendance.Attendance.AttendanceRecord.Dto.AttendanceRecordResponse;
import com.Attendance.Attendance.AttendanceRecord.Entity.AttendanceRecord;
import com.Attendance.Attendance.AttendanceRecord.Repository.AttendanceRecordRepository;
import com.Attendance.Attendance.User.Entity.User;
import com.Attendance.Attendance.User.Repository.UserRepository;
import com.Attendance.Attendance.common.AttendanceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceRecordService {

    private final AttendanceRecordRepository attendanceRepository;
    private final UserRepository userRepository;

    private static final ZoneId EGYPT_ZONE =
            ZoneId.of("Africa/Cairo");

    @Transactional
    public AttendanceRecordResponse checkIn(Long employeeId) {

        User employee = userRepository.findById(employeeId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Employee not found"
                ));

        if (!employee.isActive()) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Employee account is inactive"
            );
        }

        LocalDate today = LocalDate.now(EGYPT_ZONE);

        if (attendanceRepository
                .findByEmployee_IdAndAttendanceDate(employeeId, today)
                .isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Attendance already recorded for today"
            );
        }

        Instant now = Instant.now();

        AttendanceRecord record = new AttendanceRecord();
        record.setEmployee(employee);
        record.setAttendanceDate(today);
        record.setCheckIn(now);

        // Temporary default until we implement schedule-based lateness.
        record.setStatus(AttendanceStatus.PRESENT);

        return AttendanceRecordResponse.from(
                attendanceRepository.save(record)
        );
    }

    @Transactional
    public AttendanceRecordResponse checkOut(Long employeeId) {

        LocalDate today = LocalDate.now(EGYPT_ZONE);

        AttendanceRecord record = attendanceRepository
                .findByEmployee_IdAndAttendanceDate(employeeId, today)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No check-in record found for today"
                ));

        if (record.getCheckOut() != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Check-out already recorded for today"
            );
        }

        record.setCheckOut(Instant.now());

        return AttendanceRecordResponse.from(
                attendanceRepository.save(record)
        );
    }

    @Transactional(readOnly = true)
    public List<AttendanceRecordResponse> getEmployeeAttendance(
            Long employeeId) {

        if (!userRepository.existsById(employeeId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Employee not found"
            );
        }

        return attendanceRepository
                .findByEmployee_IdOrderByAttendanceDateDesc(employeeId)
                .stream()
                .map(AttendanceRecordResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AttendanceRecordResponse> getAttendanceBetweenDates(
            LocalDate startDate,
            LocalDate endDate) {

        if (startDate == null || endDate == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Start date and end date are required"
            );
        }

        if (startDate.isAfter(endDate)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Start date must be before or equal to end date"
            );
        }

        return attendanceRepository
                .findByAttendanceDateBetweenOrderByAttendanceDateDesc(
                        startDate,
                        endDate
                )
                .stream()
                .map(AttendanceRecordResponse::from)
                .toList();
    }
}