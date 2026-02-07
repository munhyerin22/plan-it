package spartaclub.planit.api.dto;

import java.time.LocalDateTime;

public record ScheduleResponseDto(
        Long id, String title, String content, String name, LocalDateTime updatedAt) {}