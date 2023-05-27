package com.hot6.pnureminder.controller;


import com.hot6.pnureminder.dto.AnnouncementResponseDto;
import com.hot6.pnureminder.entity.Announcement;
import com.hot6.pnureminder.repository.CustomMajorAnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("api/announce")
@RequiredArgsConstructor
@RestController
public class AnnouncementController {
    private final CustomMajorAnnouncementRepository customMajorAnnouncementRepository;

    @GetMapping("/{department}")
    public AnnouncementResponseDto getAnnouncementByDepartment(@PathVariable String department) {
        List<Announcement> announcements = customMajorAnnouncementRepository.findAllAnnouncementsByDepartment(department);
        return AnnouncementResponseDto.toDto(department, announcements);
    }
}