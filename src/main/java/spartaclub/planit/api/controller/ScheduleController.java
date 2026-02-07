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

    // 생성 - 엔티티에 지정해 놓은 값 -> Service의 create에 보내기 // 요청에 대응하는 응답을 받아 retrun.(완)
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> create(@RequestBody ScheduleRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.create(request));
    }

    // 전체 조회
    @GetMapping
    public ResponseEntity<List<GetOneScheduleResponse>> getAll(@RequestParam(required = false) String name) {
        return ResponseEntity.ok(scheduleService.getAll(name));
    }

    // 단 건 조회
    @GetMapping("/{id}")
    public ResponseEntity<GetOneScheduleResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.getOne(id));
    }

    // 수정
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> update(@PathVariable Long id, @RequestBody ScheduleRequestDto request) {
        return ResponseEntity.ok(scheduleService.update(id, request));
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestParam String password) {
        scheduleService.delete(id, password);
        return ResponseEntity.noContent().build();
    }
}