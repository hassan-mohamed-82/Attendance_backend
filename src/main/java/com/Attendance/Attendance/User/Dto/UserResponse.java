package com.Attendance.Attendance.User.Dto;

import com.Attendance.Attendance.User.Entity.User;
import com.Attendance.Attendance.common.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    private Long id;
    private String fullName;
    private String email;
    private UserRole role;
    private boolean active;

    private Long departmentId;
    private String departmentName;

    private Long workScheduleId;
    private String workScheduleName;

    public static UserResponse from(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .active(user.isActive())
                .departmentId(
                        user.getDepartment() != null
                                ? user.getDepartment().getId()
                                : null
                )
                .departmentName(
                        user.getDepartment() != null
                                ? user.getDepartment().getName()
                                : null
                )
                .workScheduleId(
                        user.getWorkSchedule() != null
                                ? user.getWorkSchedule().getId()
                                : null
                )
                .workScheduleName(
                        user.getWorkSchedule() != null
                                ? user.getWorkSchedule().getName()
                                : null
                )
                .build();
    }
}