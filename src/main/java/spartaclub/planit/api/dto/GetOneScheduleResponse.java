package spartaclub.planit.api.dto;

import java.time.LocalDateTime;

public record GetOneScheduleResponse(
        Long id, String title, String content, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {}