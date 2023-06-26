package com.hot6.pnureminder.repository;

import com.hot6.pnureminder.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

    @Repository
    public interface BuildingRepository extends JpaRepository<Building, Long> {
        Optional<Building> findByBuildingNum (Integer buildingNum);

        Optional<Building> findByBuildingName(String buildingName);

        @Query(value = "SELECT building_num, building_name, building_lat, building_lng, ST_Distance_Sphere(POINT(:longitude, :latitude), POINT(building_lng, building_lat)) AS distance FROM building_location ORDER BY distance", nativeQuery = true)
        List<Object[]> findNearestBuildingsWithDistance(double latitude, double longitude);
    }

