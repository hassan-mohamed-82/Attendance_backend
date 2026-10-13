package com.Attendance.Attendance.Department.Dto;

import com.Attendance.Attendance.Department.Entity.Department;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DepartmentResponse {

    private Long id;
    private String name;
    private String description;

    public static DepartmentResponse from(Department department) {
        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .description(department.getDescription())
                .build();
    }
}