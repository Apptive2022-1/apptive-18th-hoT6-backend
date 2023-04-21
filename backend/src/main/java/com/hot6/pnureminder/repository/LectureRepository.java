package com.hot6.pnureminder.repository;

import com.hot6.pnureminder.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Integer> {

    List<Lecture> findAllBy (Integer buildingNum);
}
