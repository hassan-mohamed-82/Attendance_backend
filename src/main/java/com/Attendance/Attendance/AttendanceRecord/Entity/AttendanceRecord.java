package com.Attendance.Attendance.AttendanceRecord.Entity;


import com.Attendance.Attendance.User.Entity.User;
import com.Attendance.Attendance.common.AttendanceStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
@Builder
@Entity
@Table(
        name = "attendance_records",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_attendance_employee_date",
                        columnNames = {"employee_id", "attendance_date"}
                )
        },
        indexes = {
                @Index(
                        name = "idx_attendance_date",
                        columnList = "attendance_date"
                )
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private User employee;

    @Column(name = "attendance_date", nullable = false)
    private LocalDate attendanceDate;

    @Column(name = "check_in")
    private Instant checkIn;

    @Column(name = "check_out")
    private Instant checkOut;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AttendanceStatus status = AttendanceStatus.PRESENT;

    @Column(length = 500)
    private String notes;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();
}
