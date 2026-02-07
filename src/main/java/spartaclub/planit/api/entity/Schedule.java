package spartaclub.planit.api.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;


@Getter
@Entity
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String title; // 일정 이름

    @Column(nullable = false)
    private String content; // 일정 내용

    @Column(nullable = false)
    private String name; //작성자 명

    @Column(nullable = false)
    private String password; //API 응답 시 DTO에서 제외.

    @CreatedDate
    @Column(updatable = false) // 생성일 수정 불가
    private LocalDateTime createdAt; // 작성일

    @LastModifiedDate
    private LocalDateTime updatedAt; // 수정일

    public Schedule(String title, String content, String name, String password, LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.name = name;
        this.password = password;
        this.createdAt = createdAt;
    }

    // 수정
    public void update(String title, String name, String password, LocalDateTime updatedAt) {
        this.title = title;
        this.name = name;
        this.password = password;
        this.updatedAt = updatedAt;
    }
}

