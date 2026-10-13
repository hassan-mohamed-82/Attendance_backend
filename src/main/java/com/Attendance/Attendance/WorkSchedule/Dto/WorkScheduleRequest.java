package com.Attendance.Attendance.WorkSchedule.Dto;

import com.Attendance.Attendance.WorkSchedule.Entity.WorkSchedule;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class WorkScheduleRequest {

    @NotBlank(message = "Work schedule name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    private LocalTime endTime;

    @NotNull(message = "Grace period is required")
    @Min(value = 0, message = "Grace period cannot be negative")
    @Max(value = 240, message = "Grace period cannot exceed 240 minutes")
    private Integer gracePeriodMinutes = 0;}

