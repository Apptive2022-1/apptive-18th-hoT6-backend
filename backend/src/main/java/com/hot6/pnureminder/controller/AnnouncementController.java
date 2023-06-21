package com.hot6.pnureminder.controller;


import com.hot6.pnureminder.dto.AnnouncementResponseDto;
import com.hot6.pnureminder.dto.Favorite.FavoriteDepartmentAnnouncementDto;
import com.hot6.pnureminder.entity.Announcement;
import com.hot6.pnureminder.entity.Favorites.FavoriteDepartment;
import com.hot6.pnureminder.entity.Member;
import com.hot6.pnureminder.repository.CustomMajorAnnouncementRepository;
import com.hot6.pnureminder.repository.Favorites.FavoriteDepartmentRepository;
import com.hot6.pnureminder.service.AnnouncementService;
import com.hot6.pnureminder.service.AuthService;
import com.hot6.pnureminder.service.Favorite.FavoriteDepartmentService;

import com.hot6.pnureminder.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("api/announce")
@RequiredArgsConstructor
@RestController
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final FavoriteDepartmentService favoriteDepartmentService;
    private final MemberService memberService;

    @GetMapping("/{department}")
    public AnnouncementResponseDto getAnnouncementByDepartment(@PathVariable String department) {
        List<Announcement> announcements = announcementService.getAnnouncementsByDepartment(department);
        return AnnouncementResponseDto.toDto(announcements);
    }

    @GetMapping("/my")
    public ResponseEntity<List<FavoriteDepartmentAnnouncementDto>> getFavorites() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Member member = memberService.findMemberByUsername(username);
        List<FavoriteDepartmentAnnouncementDto> favorites = favoriteDepartmentService.getFavoriteDepartments(member);
        return ResponseEntity.ok().body(favorites);
    }


}