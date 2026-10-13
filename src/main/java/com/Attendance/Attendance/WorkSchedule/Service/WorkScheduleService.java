package com.Attendance.Attendance.WorkSchedule.Service;
import com.Attendance.Attendance.WorkSchedule.Dto.WorkScheduleRequest;
import com.Attendance.Attendance.WorkSchedule.Dto.WorkScheduleResponse;
import com.Attendance.Attendance.WorkSchedule.Entity.WorkSchedule;
import com.Attendance.Attendance.WorkSchedule.Repository.WorkScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkScheduleService {
    private final WorkScheduleRepository workScheduleRepository;
    @Transactional
    public WorkScheduleResponse createWorkSchedule(WorkScheduleRequest request) {

        if (request.getStartTime() != null
                && request.getEndTime() != null
                && !request.getEndTime().isAfter(request.getStartTime())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "End time must be after start time"
            );
        }

        if (workScheduleRepository.existsByName(request.getName())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Work schedule name already exists"
            );
        }

        WorkSchedule workSchedule = WorkSchedule.builder()
                .name(request.getName())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .gracePeriodMinutes(request.getGracePeriodMinutes())
                .build();

        return WorkScheduleResponse.from(
                workScheduleRepository.save(workSchedule)
        );
    }

    @Transactional(readOnly = true)
    public List<WorkScheduleResponse> getAllWorkSchedules() {

        return workScheduleRepository.findAll()
                .stream()
                .map(WorkScheduleResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public WorkScheduleResponse getWorkScheduleById(Long id) {

        WorkSchedule workSchedule = workScheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Work schedule not found"
                ));

        return WorkScheduleResponse.from(workSchedule);
    }

    @Transactional
    public WorkScheduleResponse updateWorkSchedule(
            Long id,
            WorkScheduleRequest request) {

        if (request.getStartTime() != null
                && request.getEndTime() != null
                && !request.getEndTime().isAfter(request.getStartTime())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "End time must be after start time"
            );
        }

        WorkSchedule workSchedule = workScheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Work schedule not found"
                ));

        if (!workSchedule.getName().equals(request.getName())
                && workScheduleRepository.existsByName(request.getName())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Work schedule name already exists"
            );
        }

        workSchedule.setName(request.getName());
        workSchedule.setStartTime(request.getStartTime());
        workSchedule.setEndTime(request.getEndTime());
        workSchedule.setGracePeriodMinutes(request.getGracePeriodMinutes());

        return WorkScheduleResponse.from(
                workScheduleRepository.save(workSchedule)
        );
    }

    @Transactional
    public void deleteWorkSchedule(Long id) {

        WorkSchedule workSchedule = workScheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Work schedule not found"
                ));

        workScheduleRepository.delete(workSchedule);
    }
}