package com.hot6.pnureminder.service;

import com.hot6.pnureminder.dto.EventDto;
import com.hot6.pnureminder.entity.Event;
import com.hot6.pnureminder.entity.Member;
import com.hot6.pnureminder.exception.ResourceNotFoundException;
import com.hot6.pnureminder.exception.UnauthorizedException;
import com.hot6.pnureminder.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final MemberService memberService;

    public EventDto createEvent(String username, EventDto eventDto) {
        Member member = memberService.findMemberByUsername(username);
        Event event = eventDto.toEntity();
        event.setMember(member);

        Event savedEvent = eventRepository.save(event);

        return EventDto.fromEntity(savedEvent);
    }

    public List<EventDto> getMonthEvents(String username, int month) {
        Long memberId = memberService.findMemberByUsername(username).getId();

        // 해당 Event가 어느 달에 존재하는지 확인
        YearMonth yearMonth = YearMonth.of(Year.now().getValue(), month);
        ZonedDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay(ZoneId.of("Asia/Seoul"));
        ZonedDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59, 999999999).atZone(ZoneId.of("Asia/Seoul"));

        // 이벤트 JPA
        List<Event> events = eventRepository.findAllByMemberIdAndStartTimeBetweenAndEndTimeBetween(memberId, startOfMonth, endOfMonth, startOfMonth, endOfMonth);

        // DTO 변환
        return events.stream().map(EventDto::fromEntity).collect(Collectors.toList());
    }
    public EventDto getEvent(String username, Long eventId){
        Long memberId = memberService.findMemberByUsername(username).getId();
        Event event = eventRepository.findByMemberIdAndEventId(memberId,eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found for this id : " + eventId));
        return EventDto.fromEntity(event);
    }

    public void deleteEvent(String username, Long eventId) {
        Member member = memberService.findMemberByUsername(username);
        Event event = eventRepository.findByMemberIdAndEventId(member.getId(), eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found for this id : " + eventId));

        if (!event.getMember().getId().equals(member.getId())) {
            throw new UnauthorizedException("You are not allowed to delete this event");
        }

        eventRepository.delete(event);
    }




}
