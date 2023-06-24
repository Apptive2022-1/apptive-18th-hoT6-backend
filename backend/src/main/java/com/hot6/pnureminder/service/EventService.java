package com.hot6.pnureminder.service;

import com.hot6.pnureminder.dto.EventDto;
import com.hot6.pnureminder.entity.Event;
import com.hot6.pnureminder.entity.Member;
import com.hot6.pnureminder.exception.ResourceNotFoundException;
import com.hot6.pnureminder.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public EventDto getEvent(String username, Long eventId){
        Long memberId = memberService.findMemberByUsername(username).getId();
        Event event = eventRepository.findByMemberIdAndEventId(memberId,eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found for this id : " + eventId));
        return EventDto.fromEntity(event);
    }

}
