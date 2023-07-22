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

    // getters and setters

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

    //회원 id는 숨김(보안상 문제)
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
