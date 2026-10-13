package com.Attendance.Attendance.WorkSchedule.Controller;


import com.Attendance.Attendance.WorkSchedule.Dto.WorkScheduleRequest;
import com.Attendance.Attendance.WorkSchedule.Dto.WorkScheduleResponse;
import com.Attendance.Attendance.WorkSchedule.Service.WorkScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/work-schedules")
@RequiredArgsConstructor
public class WorkScheduleController {

    private final WorkScheduleService workScheduleService;

    @PostMapping
    public ResponseEntity<WorkScheduleResponse> create(
            @Valid @RequestBody WorkScheduleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(workScheduleService.createWorkSchedule(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkScheduleResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody WorkScheduleRequest request) {
        return ResponseEntity.ok(workScheduleService.updateWorkSchedule(id, request));
    }

    @GetMapping
    public ResponseEntity<List<WorkScheduleResponse>> listAll() {
        return ResponseEntity.ok(workScheduleService.getAllWorkSchedules());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkScheduleResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(workScheduleService.getWorkScheduleById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        workScheduleService.deleteWorkSchedule(id);
        return ResponseEntity.noContent().build();
    }
}