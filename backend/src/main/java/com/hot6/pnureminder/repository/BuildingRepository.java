package com.hot6.pnureminder.repository;

import com.hot6.pnureminder.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {
    Optional<Building> findByBuildingNum (Integer buildingNum);


}
