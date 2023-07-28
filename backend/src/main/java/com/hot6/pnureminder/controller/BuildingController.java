package com.hot6.pnureminder.controller;

import com.hot6.pnureminder.dto.BuildingResponseDto;
import com.hot6.pnureminder.dto.Favorite.FavoriteBuildingRoomListDto;
import com.hot6.pnureminder.dto.LectureRoomDto;
import com.hot6.pnureminder.entity.Building;
import com.hot6.pnureminder.entity.Member;
import com.hot6.pnureminder.service.BuildingService;
import com.hot6.pnureminder.service.Favorite.FavoriteBuildingService;
import com.hot6.pnureminder.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/nearest-buildings")
public class BuildingController {
    private final BuildingService buildingService;
    private final MemberService memberService;
    private final FavoriteBuildingService favoriteBuildingService;


//    deprecated
    @GetMapping("/test")
    public List<Building> getNearestBuildings(
            @RequestParam("user_latitude") double latitude,
            @RequestParam("user_longitude") double longitude) {
        return buildingService.findNearestBuildings(latitude, longitude);
    }
    @GetMapping("/byloc")
    public List<BuildingResponseDto> getNearestBuildingsWithAvailableLectureRoom(
            @RequestParam("user_latitude") double latitude,
            @RequestParam("user_longitude") double longitude,
            @RequestParam("user_settime") int setMinutes
    ) {
        return buildingService.findNearestBuildingsWithAvailableLectureRooms(latitude, longitude, setMinutes);
    }

    @GetMapping("/{buildingName}")
    public List<LectureRoomDto> getRoomByBuildingName(@PathVariable String buildingName, @RequestParam(required = false) int setMinutes) {
        return buildingService.findRoomsByBuildingNameAndSetTimeNow(buildingName);
    }

    @PostMapping("/{buildingName}/like")
    public ResponseEntity<?> addFavorite(@PathVariable String buildingName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Member member = memberService.findMemberByUsername(username);

        boolean isAdded = favoriteBuildingService.addFavorite(member, buildingName);

        if (isAdded) {
            String message = buildingName + "이(가) 즐겨찾기에 추가되었습니다.";
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.badRequest().body(buildingName + "이(가) 이미 즐겨찾기에 있습니다.");
        }
    }

    @PostMapping("/{buildingName}/unlike")
    public ResponseEntity<?> removeFavorite(@PathVariable String buildingName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Member member = memberService.findMemberByUsername(username);

        boolean isRemoved = favoriteBuildingService.removeFavorite(member, buildingName);

        if (isRemoved) {
            String message = buildingName + "이(가) 즐겨찾기에서 해제되었습니다.";
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.badRequest().body(buildingName + "이(가) 즐겨찾기에 없습니다.");
        }
    }

    @GetMapping("/my")
    public ResponseEntity<List<FavoriteBuildingRoomListDto>> getFavoriteBuildings() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Member member = memberService.findMemberByUsername(username);
        List<FavoriteBuildingRoomListDto> favorites = favoriteBuildingService.getFavoriteBuildings(member);
        return ResponseEntity.ok().body(favorites);
    }

    @GetMapping("/main")
    public ResponseEntity<String> tester(){
        return ResponseEntity.ok("main page test");
    }

}
