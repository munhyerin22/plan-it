package spartaclub.planit.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spartaclub.planit.api.dto.GetOneScheduleResponse;
import spartaclub.planit.api.dto.ScheduleRequestDto;
import spartaclub.planit.api.dto.ScheduleResponseDto;
import spartaclub.planit.api.entity.Schedule;
import spartaclub.planit.api.repository.ScheduleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleResponseDto create(ScheduleRequestDto request){
        Schedule schedule = new Schedule(request.title(), request.content(), request.name(), request.password());
        Schedule saved = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(saved.getId(), saved.getTitle(), saved.getContent(), saved.getName(), saved.getCreatedAt(), saved.getUpdatedAt());
    }

    @Transactional(readOnly = true)
    public GetOneScheduleResponse getOne(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("일정이 없습니다."));
        return new GetOneScheduleResponse(schedule.getId(), schedule.getTitle(), schedule.getContent(), schedule.getName(), schedule.getCreatedAt(), schedule.getUpdatedAt());
    }

    @Transactional(readOnly = true)
    public List<GetOneScheduleResponse> getAll(String name){
        List<Schedule> schedules = (name != null)
                ? scheduleRepository.findAllByNameOrderByUpdatedAtDesc(name)
                : scheduleRepository.findAllByOrderByUpdatedAtDesc();

        return schedules.stream()
                .map(s -> new GetOneScheduleResponse(s.getId(), s.getTitle(), s.getContent(), s.getName(), s.getCreatedAt(), s.getUpdatedAt()))
                .toList();
    }

    @Transactional
    public ScheduleResponseDto update(Long id, ScheduleRequestDto request) {
        Schedule schedule = findAndVerify(id, request.password());
        schedule.update(request.title(), request.name());
        return new ScheduleResponseDto(schedule.getId(), schedule.getTitle(), schedule.getContent(), schedule.getName(), schedule.getCreatedAt(), schedule.getUpdatedAt());
    }

    @Transactional
    public void delete(Long id, String password) {
        Schedule schedule = findAndVerify(id, password);
        scheduleRepository.delete(schedule);
    }

    private Schedule findAndVerify(Long id, String password) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("일정이 없습니다."));
        if (!schedule.getPassword().equals(password)) throw new IllegalArgumentException("비밀번호 불일치");
        return schedule;
    }
}