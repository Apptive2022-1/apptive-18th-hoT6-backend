package com.hot6.pnureminder.repository;

import com.hot6.pnureminder.entity.LectureRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRoomRepository extends JpaRepository<LectureRoom, Integer> {
    List<LectureRoom> findAllByBuildingNum(Integer buildingNum);
}