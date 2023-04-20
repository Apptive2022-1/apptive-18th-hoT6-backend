package com.hot6.pnureminder.controller;

import com.hot6.pnureminder.entity.Building;
import com.hot6.pnureminder.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/nearest-buildings")
public class BuildingController {
    @Autowired
    private BuildingService buildingService;

    @GetMapping("/test")
    public List<Building> getNearestBuildings(
            @RequestParam("user_latitude") double latitude,
            @RequestParam("user_longitude") double longitude) {
        return buildingService.findNearestBuildings(latitude, longitude);
    }

    @GetMapping("/main")
    public ResponseEntity<String> tester(){
        return ResponseEntity.ok("main page test");
    }

}
