package com.hot6.pnureminder.service;

import com.hot6.pnureminder.dto.BuildingResponseDto;
import com.hot6.pnureminder.dto.LectureDto;
import com.hot6.pnureminder.dto.LectureRoomDto;
import com.hot6.pnureminder.entity.Building;
import com.hot6.pnureminder.repository.BuildingRepository;
import com.hot6.pnureminder.util.DateTimeUtils;
import com.hot6.pnureminder.util.haversineDistance;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Transactional
public class BuildingService {


    private final BuildingRepository buildingRepository;
    private final LectureRoomService lectureRoomService;
    private final LectureService lectureService;


    public List<Building> findNearestBuildings(double latitude, double longitude) {
        List<Building> buildings = buildingRepository.findAll();

        Map<Building, Double> buildingDistances = new ConcurrentHashMap<>();

        for (Building building : buildings) {
            double distance = haversineDistance.getDistance(latitude, longitude, building.getBuildingLat(), building.getBuildingLng());
            buildingDistances.put(building, distance);
        }

// 거리에 따라 건물을 정렬
        List<Building> sortedBuildings = buildings.stream()
                .sorted(Comparator.comparing(buildingDistances::get))
                .collect(Collectors.toList());

// 가장 가까운 세 건물을 선택
        return sortedBuildings.subList(0, Math.min(3, sortedBuildings.size()));
    }


    //건물이 주어졌을때 현재 시각, 지정한 시간, 요일을 가져와서 가능한 강의실 추출
    public List<LectureRoomDto> findAvailableLectureRoomsWithSetTime(Building building, int setMinutes, ZonedDateTime currentTime) {
        List<LectureRoomDto> lectureRooms = lectureRoomService.findAllByBuildingNum(building.getBuildingNum());

//       현재시간 추출
//       현재 시간에서 세팅한 시간을 더해서 마진을 만든다
        ZonedDateTime marginTime = currentTime.plus(setMinutes, ChronoUnit.MINUTES);

//       현재요일 추출 테스트
        //int currentDayOfWeek = (DayOfWeek.from(LocalDate.now()).getValue())-1;
        int currentDayOfWeek = 0;

//       강의실 리스트 가져와서(LectureRoom pk로) 비어있는 강의실 확인 후 리스트화
        List<LectureRoomDto> availableLectureRooms = new ArrayList<>();

        for (LectureRoomDto lectureRoomDto : lectureRooms) {
            //List<LectureDto> lecturesInRoom = lectureService.findAllByLectureRoomId(lectureRoomDto.getId());
            List<LectureDto> lecturesInRoom = lectureService.findAllByLectureRoomIdAndDayOfWeek(lectureRoomDto.getId(),currentDayOfWeek);

            boolean isAvailableRoom = false;

            for (LectureDto lectureDto : lecturesInRoom) {
                ZonedDateTime lectureStartTime = lectureDto.getStartTime().toLocalTime().atDate(LocalDate.now()).atZone(ZoneId.of("Asia/Seoul"));
                ZonedDateTime lectureEndTime = lectureStartTime.plus(lectureDto.getRunTime().toLocalTime().getHour(), ChronoUnit.HOURS).plus(lectureDto.getRunTime().toLocalTime().getMinute(), ChronoUnit.MINUTES);


//                요일이 같고 현재 시간이 강의 시간+세팅시간과 겹칠때 isAvailable false
                boolean isEmptyNow = (marginTime.isBefore(lectureStartTime) || currentTime.isAfter(lectureEndTime));
                boolean isToday = lectureDto.getDayOfWeek() == currentDayOfWeek;
                if ( isToday && isEmptyNow) {
                    isAvailableRoom = true;
                    break;
                }
            }

            if (isAvailableRoom) {
                lectureRoomDto.getId()
                availableLectureRooms.add(lectureRoomDto);
            }
        }

        return availableLectureRooms;
    }

    public List<BuildingResponseDto> findNearestBuildingsWithAvailableLectureRooms(double latitude, double longitude, int setMinutes) {
        List<Building> nearestBuildings = findNearestBuildings(latitude, longitude);
        List<BuildingResponseDto> buildingResponseDtoList = new ArrayList<>();

        ZonedDateTime currentTime = DateTimeUtils.getCurrentSeoulTime();

        for (Building building : nearestBuildings) {
            List<LectureRoomDto> availableLectureRooms = findAvailableLectureRoomsWithSetTime(building, setMinutes, currentTime);
            BuildingResponseDto buildingResponseDto = BuildingResponseDto.toDto(building, availableLectureRooms);
            buildingResponseDtoList.add(buildingResponseDto);
            System.out.println(availableLectureRooms);
        }
        return buildingResponseDtoList;
    }
}

