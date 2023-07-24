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

import java.security.Principal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

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
    @GetMapping
    public ResponseEntity<List<EventDto>> getMonthEvents(@RequestParam(value = "month", required = false) Integer month, Principal principal) {
        if (month == null) {
            // If month is not specified, use the current month
            month = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).getMonthValue();
        }

        List<EventDto> events = eventService.getMonthEvents(principal.getName(), month);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDto> getEvent(@PathVariable Long eventId, Principal principal) {
        EventDto eventDto = eventService.getEvent(principal.getName(), eventId);
        return new ResponseEntity<>(eventDto, HttpStatus.OK);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable Long eventId, @RequestBody EventDto eventDto, Principal principal) {
        EventDto updatedEventDto = eventService.updateEvent(principal.getName(), eventId, eventDto);
        return new ResponseEntity<>(updatedEventDto, HttpStatus.OK);
    }


    @DeleteMapping("/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long eventId, Principal principal) {
        eventService.deleteEvent(principal.getName(), eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
