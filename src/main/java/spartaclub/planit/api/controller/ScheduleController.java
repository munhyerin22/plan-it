package spartaclub.planit.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spartaclub.planit.api.dto.GetOneScheduleResponse;
import spartaclub.planit.api.dto.ScheduleRequestDto;
import spartaclub.planit.api.dto.ScheduleResponseDto;
import spartaclub.planit.api.service.ScheduleService;

import java.util.List;

@RestController
@RequestMapping("/api/schedules") // 공통 경로 설정
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> create(@RequestBody ScheduleRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<GetOneScheduleResponse>> getAll(@RequestParam(required = false) String name) {
        return ResponseEntity.ok(scheduleService.getAll(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetOneScheduleResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.getOne(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> update(@PathVariable Long id, @RequestBody ScheduleRequestDto request) {
        return ResponseEntity.ok(scheduleService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestParam String password) {
        scheduleService.delete(id, password);
        return ResponseEntity.noContent().build();
    }
}