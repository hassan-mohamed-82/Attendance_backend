package com.Attendance.Attendance.Department.Repository;

import com.Attendance.Attendance.Department.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    boolean existsByName(String name);
}
