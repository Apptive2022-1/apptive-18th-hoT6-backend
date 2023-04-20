package com.hot6.pnureminder.service;

import com.hot6.pnureminder.entity.Building;
import com.hot6.pnureminder.repository.BuildingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    public List<Building> findNearestBuildings(double latitude, double longitude) {
        List<Building> buildings = buildingRepository.findAll();

        Map<Building, Double> buildingDistances = new HashMap<>();
        for (Building building : buildings) {
            double distance = haversineDistance(latitude, longitude, building.getBuildingLat(), building.getBuildingLng());
            buildingDistances.put(building, distance);
        }

// 거리에 따라 건물을 정렬합니다.
        List<Building> sortedBuildings = buildings.stream()
                .sorted(Comparator.comparing(buildingDistances::get))
                .collect(Collectors.toList());

// 가장 가까운 세 건물을 선택합니다.
        List<Building> nearestThreeBuildings = sortedBuildings.subList(0, Math.min(3, sortedBuildings.size()));

        return nearestThreeBuildings;
    }

    private double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = c;

        return distance;
    }
}
