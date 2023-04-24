package com.hot6.pnureminder.service;

import com.hot6.pnureminder.dto.AnnualPlanResponseDto;
import com.hot6.pnureminder.dto.BuildingResponseDto;
import com.hot6.pnureminder.dto.LectureDto;
import com.hot6.pnureminder.dto.LectureRoomDto;
import com.hot6.pnureminder.entity.Building;
import com.hot6.pnureminder.entity.Lecture;
import com.hot6.pnureminder.entity.LectureRoom;
import com.hot6.pnureminder.repository.BuildingRepository;
import com.hot6.pnureminder.repository.LectureRepository;
import com.hot6.pnureminder.repository.LectureRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BuildingService {


    private final BuildingRepository buildingRepository;
    private final LectureRepository lectureRepository;

    @Autowired
    private LectureRoomService lectureRoomService;
    @Autowired
    private LectureService lectureService;


    public List<Building> findNearestBuildings(double latitude, double longitude) {
        List<Building> buildings = buildingRepository.findAll();

        Map<Building, Double> buildingDistances = new HashMap<>();
        for (Building building : buildings) {
            double distance = haversineDistance(latitude, longitude, building.getBuildingLat(), building.getBuildingLng());
            buildingDistances.put(building, distance);
        }

// 거리에 따라 건물을 정렬
        List<Building> sortedBuildings = buildings.stream()
                .sorted(Comparator.comparing(buildingDistances::get))
                .collect(Collectors.toList());

// 가장 가까운 세 건물을 선택
        return sortedBuildings.subList(0, Math.min(3, sortedBuildings.size()));
    }

    private double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double atan2Value = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = atan2Value;

        return distance;
    }


    //건물이 주어졌을때 현재 시각, 지정한 시간, 요일을 가져와서 가능한 강의실 추출
    public List<LectureRoomDto> findAvailableLectureRooms(Building building) {
        List<LectureRoomDto> lectureRooms = lectureRoomService.findAllByBuildingNum(building.getBuildingNum());

//       현재시간 추출
        LocalTime currentTime = LocalTime.now();

//       현재요일 추출
        int currentDayOfWeek = (DayOfWeek.from(LocalDate.now()).getValue())-1;

//       강의실 리스트 가져와서(LectureRoom pk로) 비어있는 강의실 확인 후 리스트화
        List<LectureRoomDto> availableLectureRooms = new ArrayList<>();

        for (LectureRoomDto lectureRoomDto : lectureRooms) {
            List<Lecture> lectures = lectureRepository.findAllByLectureRoomId(lectureRoomDto.getId());

            boolean isAvailableRoom = true;

            for (Lecture lecture : lectures) {
                LocalTime lectureStartTime = lecture.getStartTime().toLocalTime();
                LocalTime lectureEndTime = lectureStartTime.plus(lecture.getRunTime().toLocalTime().getHour(), ChronoUnit.HOURS).plus(lecture.getRunTime().toLocalTime().getMinute(), ChronoUnit.MINUTES);
//                요일이 같고 현재 시간이 강의 시간과 겹칠때 isAvailable false
                if (lecture.getDayOfWeek() == currentDayOfWeek && (currentTime.isAfter(lectureStartTime) && currentTime.isBefore(lectureEndTime))) {
                    isAvailableRoom = false;
                    break;
                }
            }

            if (isAvailableRoom) {
                availableLectureRooms.add(lectureRoomDto);
            }
        }

        return availableLectureRooms;
    }


    //건물이 주어졌을때 현재 시각, 지정한 시간, 요일을 가져와서 가능한 강의실 추출
    public List<LectureRoomDto> findAvailableLectureRoomsWithSetTime(Building building, int setMinutes) {
        List<LectureRoomDto> lectureRooms = lectureRoomService.findAllByBuildingNum(building.getBuildingNum());

//       현재시간 추출
        LocalTime currentTime = LocalTime.now();
//       현재 시간에서 세팅한 시간을 더해서 마진을 만든다
        LocalTime endTime = currentTime.plus(setMinutes, ChronoUnit.MINUTES);

//       현재요일 추출
        int currentDayOfWeek = (DayOfWeek.from(LocalDate.now()).getValue())-3;

//       강의실 리스트 가져와서(LectureRoom pk로) 비어있는 강의실 확인 후 리스트화
        List<LectureRoomDto> availableLectureRooms = new ArrayList<>();

        for (LectureRoomDto lectureRoomDto : lectureRooms) {
            List<LectureDto> lectureDtos = lectureService.findAllByLectureRoomId(lectureRoomDto.getId());

            boolean isAvailableRoom = true;

            for (LectureDto lectureDto : lectureDtos) {
                LocalTime lectureStartTime = lectureDto.getStartTime().toLocalTime();
                LocalTime lectureEndTime = lectureStartTime.plus(lectureDto.getRunTime().toLocalTime().getHour(), ChronoUnit.HOURS).plus(lectureDto.getRunTime().toLocalTime().getMinute(), ChronoUnit.MINUTES);
//                요일이 같고 현재 시간이 강의 시간+세팅시간과 겹칠때 isAvailable false
                if (lectureDto.getDayOfWeek() == currentDayOfWeek && (endTime.isAfter(lectureStartTime) && currentTime.isBefore(lectureEndTime))) {
                    isAvailableRoom = false;
                    break;
                }
            }

            if (isAvailableRoom) {
                availableLectureRooms.add(lectureRoomDto);
            }
        }

        return availableLectureRooms;
    }

    public List<BuildingResponseDto> findNearestBuildingsWithAvailableLectureRooms(double latitude, double longitude, int setMinutes) {
        List<Building> nearestBuildings = findNearestBuildings(latitude, longitude);
        List<BuildingResponseDto> nearestBuildingsDtos = new ArrayList<>();


        for (Building building : nearestBuildings) {
            List<LectureRoomDto> availableLectureRooms = findAvailableLectureRoomsWithSetTime(building, setMinutes);
            BuildingResponseDto buildingResponseDto = BuildingResponseDto.toDto(building, availableLectureRooms);
            nearestBuildingsDtos.add(buildingResponseDto);
            System.out.println(availableLectureRooms);
            System.out.println("aaa");
        }
        return nearestBuildingsDtos;
    }
}

