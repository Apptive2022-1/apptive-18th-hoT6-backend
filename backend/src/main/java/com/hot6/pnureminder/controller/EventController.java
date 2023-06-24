package com.hot6.pnureminder.controller;

import com.hot6.pnureminder.dto.EventDto;
import com.hot6.pnureminder.entity.Event;
import com.hot6.pnureminder.entity.Member;
import com.hot6.pnureminder.service.EventService;
import com.hot6.pnureminder.service.MemberService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RequestMapping("/api/events")
@RequiredArgsConstructor
@RestController
public class EventController {

    private final EventService eventService;
    private final MemberService memberService;


    @PostMapping
    public ResponseEntity<EventDto> createEvent(@RequestBody EventDto eventDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        EventDto createdEvent = eventService.createEvent(username, eventDto);

        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDto> getEvent(@PathVariable Long eventId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        EventDto eventDto = eventService.getEvent(username, eventId);
        return new ResponseEntity<>(eventDto, HttpStatus.OK);
    }


}
