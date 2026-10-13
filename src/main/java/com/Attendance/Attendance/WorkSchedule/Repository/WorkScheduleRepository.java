package com.Attendance.Attendance.WorkSchedule.Repository;

import com.Attendance.Attendance.WorkSchedule.Entity.WorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkScheduleRepository extends JpaRepository<WorkSchedule,Long> {

    boolean existsByName(String name);}
