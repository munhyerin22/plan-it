package spartaclub.planit.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spartaclub.planit.api.dto.GetOneScheduleResponse;
import spartaclub.planit.api.dto.ScheduleRequestDto;
import spartaclub.planit.api.dto.ScheduleResponseDto;
import spartaclub.planit.api.entity.Schedule;
import spartaclub.planit.api.repository.ScheduleRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    // 생성 사용자에게 입력받은 값을 Controller에서 받아서 scheduleRepository를 통해 ResponseDto로 저장.
    @Transactional
    public ScheduleResponseDto create(ScheduleRequestDto request){
        Schedule schedule = new Schedule(
                request.title(),
                request.content(),
                request.name(),
                request.password(),
                LocalDateTime.now());
        Schedule saved = scheduleRepository.save(schedule);
        return new ScheduleResponseDto( //ResponseDto -> 요청에 응답할 내용
                saved.getId(),
                saved.getTitle(),
                saved.getContent(),
                saved.getName(),
                saved.getCreatedAt()
        );
    }

    // 단 건 조회
    @Transactional(readOnly = true) //읽기 전용
    public GetOneScheduleResponse getOne(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(()
                // scheduleRepository에서 해당하는 id를 찾아 값이 없으면
                -> new IllegalArgumentException("일정이 없습니다."));
        //값이 있으면 return.
        return new GetOneScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getName(),
                schedule.getCreatedAt());
    }

    // 전체 조회
    @Transactional(readOnly = true) //읽기 전용
    public List<GetOneScheduleResponse> getAll(String name){ // name-> 작성자를 기준으로 그 사람이 쓴 글 전체 조회.
        List<Schedule> schedules = (name != null)
                ? scheduleRepository.findAllByNameOrderByUpdatedAtDesc(name)
                : scheduleRepository.findAllByOrderByUpdatedAtDesc();

        return schedules.stream()
                .map(s -> new GetOneScheduleResponse(
                        s.getId(),
                        s.getTitle(),
                        s.getContent(),
                        s.getName(),
                        s.getCreatedAt()
                ))
                .toList();
    }

    // 수정(update)
    @Transactional
    public ScheduleResponseDto update(Long id, ScheduleRequestDto request) {
        Schedule schedule = findAndVerify(id, request.password()); // 수정 시 id와 password 요청.
        schedule.update( // 수정시 입력할 값+수정일(시간)
                request.title(),
                request.name(),
                request.password(),
                LocalDateTime.now());
        return new ScheduleResponseDto( // 응답
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getName(),
                schedule.getUpdatedAt());
    }

    // 삭제
    @Transactional
    public void delete(Long id, String password) {
        Schedule schedule = findAndVerify(id, password); //삭제 시 id와 password 요청
        scheduleRepository.delete(schedule); // 해당하는 내용 삭제
    }

    private Schedule findAndVerify(Long id, String password) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("일정이 없습니다."));
        if (!schedule.getPassword().equals(password)) throw new IllegalArgumentException("비밀번호 불일치");
        return schedule;
    }
}