package com.Attendance.Attendance.WorkSchedule.Dto;

import com.Attendance.Attendance.WorkSchedule.Entity.WorkSchedule;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Builder
@Getter
public class WorkScheduleResponse {

    private Long id;
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer gracePeriodMinutes;

    public static WorkScheduleResponse from(WorkSchedule workSchedule) {
        return WorkScheduleResponse.builder()
                .id(workSchedule.getId())
                .name(workSchedule.getName())
                .startTime(workSchedule.getStartTime())
                .endTime(workSchedule.getEndTime())
                .gracePeriodMinutes(workSchedule.getGracePeriodMinutes())
                .build();
    }
}
