package com.Attendance.Attendance.Auth.Repository;

import com.Attendance.Attendance.Auth.Entity.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {
    Optional<AttendanceRecord> findByEmployee_IdAndAttendanceDate(
            Long employeeId,
            LocalDate attendanceDate
    );

    List<AttendanceRecord> findByEmployee_IdOrderByAttendanceDateDesc(
            Long employeeId
    );

    List<AttendanceRecord> findByAttendanceDateBetweenOrderByAttendanceDateDesc(
            LocalDate startDate,
            LocalDate endDate
    );
}
