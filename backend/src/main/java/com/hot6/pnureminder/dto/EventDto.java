package com.hot6.pnureminder.dto;

import com.hot6.pnureminder.entity.Event;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {

    private Long eventId;
    private Long memberId;
    private String title;
    private String description;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String color;
    private int alarmTime;


    //객체 변환은 인스턴스 메서드로
    public Event toEntity() {
        return Event.builder()
                .eventId(eventId)
                .title(title)
                .description(description)
                .location(location)
                .startTime(startTime)
                .endTime(endTime)
                .color(color)
                .alarmTime(alarmTime)
                .build();
    }

    //객체 수정은 인스턴스 메서드로
    public void updateFrom(EventDto source) {
        this.title = source.title;
        this.description = source.description;
        this.location = source.location;
        this.startTime = source.startTime;
        this.endTime = source.endTime;
        this.color = source.color;
        this.alarmTime = source.alarmTime;
    }

    //객체 생성은 정적 메서드로 중앙집중화
    public static EventDto fromEntity(Event event) {
        return EventDto.builder()
                .eventId(event.getEventId())
                .title(event.getTitle())
                .description(event.getDescription())
                .location(event.getLocation())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .color(event.getColor())
                .alarmTime(event.getAlarmTime())
                .build();
    }
}
