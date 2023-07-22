package com.hot6.pnureminder.controller;


import com.hot6.pnureminder.dto.AnnouncementResponseDto;
import com.hot6.pnureminder.dto.Favorite.FavoriteDepartmentAnnouncementDto;
import com.hot6.pnureminder.entity.Announcement;
import com.hot6.pnureminder.entity.Member;
import com.hot6.pnureminder.service.AnnouncementService;
import com.hot6.pnureminder.service.DepartmentService;
import com.hot6.pnureminder.service.Favorite.FavoriteDepartmentService;

import com.hot6.pnureminder.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@RequestMapping("api/announce")
@RequiredArgsConstructor
@RestController
public class AnnouncementController {

    private final AnnouncementService announcementService;
    private final FavoriteDepartmentService favoriteDepartmentService;
    private final MemberService memberService;
    private final DepartmentService departmentService;

    @GetMapping("/{departmentName}")
    public AnnouncementResponseDto getAnnouncementByDepartment(@PathVariable String departmentName, @RequestParam(required = false) String keyword) {
        List<Announcement> announcements = announcementService.getAnnouncementsByDepartmentNameAndKeyword(departmentName, keyword);
        return AnnouncementResponseDto.toDto(announcements);
    }
//토글방식
//    @PostMapping("/{departmentName}/like")
//    public ResponseEntity<?> addFavorite(@PathVariable String departmentName) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String username = auth.getName();
//
//        Member member = memberService.findMemberByUsername(username);
//
//        boolean isAdded = favoriteDepartmentService.toggleFavorite(member, departmentName);
//
//        String message = isAdded ? departmentName+"가 즐겨찾기에 추가되었습니다." : departmentName+"가 즐겨찾기에서 해제되었습니다.";
//
//        return ResponseEntity.ok(message);
//    }

    @PostMapping("/{departmentName}/like")
    public ResponseEntity<?> addFavorite(@PathVariable String departmentName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Member member = memberService.findMemberByUsername(username);

        boolean isAdded = favoriteDepartmentService.addFavorite(member, departmentName);

        if (isAdded) {
            String message = departmentName + "가 즐겨찾기에 추가되었습니다.";
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.badRequest().body(departmentName + "는 이미 즐겨찾기에 있습니다.");
        }
    }

    @PostMapping("/{departmentName}/unlike")
    public ResponseEntity<?> removeFavorite(@PathVariable String departmentName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Member member = memberService.findMemberByUsername(username);

        boolean isRemoved = favoriteDepartmentService.removeFavorite(member, departmentName);

        if (isRemoved) {
            String message = departmentName + "가 즐겨찾기에서 해제되었습니다.";
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.badRequest().body(departmentName + "는 즐겨찾기에 없습니다.");
        }
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