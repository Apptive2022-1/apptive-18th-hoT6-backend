package com.hot6.pnureminder.service;

import com.hot6.pnureminder.dto.BuildingResponseDto;
import com.hot6.pnureminder.dto.LectureInfoDto;
import com.hot6.pnureminder.dto.LectureRoomDto;
import com.hot6.pnureminder.entity.Building;
import com.hot6.pnureminder.entity.Lecture;
import com.hot6.pnureminder.entity.LectureRoom;
import com.hot6.pnureminder.repository.BuildingRepository;
import com.hot6.pnureminder.util.DateTimeUtilsForTest;
import com.hot6.pnureminder.util.haversineDistance;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
        List<Object[]> results = buildingRepository.findNearestBuildingsWithDistance(latitude, longitude);

        // 거리에 따라 건물을 정렬하고 가장 가까운 세 건물을 선택
        return results.stream()
                .map(result -> {
                    Building building = new Building();
                    building.setBuildingNum((Integer) result[0]);
                    building.setBuildingName((String) result[1]);
                    building.setBuildingLat(((BigDecimal) result[2]).doubleValue());
                    building.setBuildingLng(((BigDecimal) result[3]).doubleValue());
                    // 필요한 나머지 필드를 여기에 추가하십시오.

                    return building;
                })
                .limit(5)
                .collect(Collectors.toList());
    }
//        List<Building> buildings = buildingRepository.findAll();
//
//        Map<Building, Double> buildingDistances = new ConcurrentHashMap<>();
//
//        for (Building building : buildings) {
//            double distance = haversineDistance.getDistance(latitude, longitude, building.getBuildingLat(), building.getBuildingLng());
//            buildingDistances.put(building, distance);
//        }
//
//// 거리에 따라 건물을 정렬
//        List<Building> sortedBuildings = buildings.stream()
//                .sorted(Comparator.comparing(buildingDistances::get))
//                .collect(Collectors.toList());
//
//// 가장 가까운 세 건물을 선택
//        return sortedBuildings.subList(0, Math.min(5, sortedBuildings.size()));
//    }

    private Optional<LectureInfoDto> isLectureRoomAvailable(List<Lecture> lecturesInRoom, ZonedDateTime currentTime, ZonedDateTime startMargin, ZonedDateTime finishMargin) {
        ZonedDateTime tempTime = DateTimeUtilsForTest.getTempTime();
        ZonedDateTime inProgressLectureEndTime = null;
        //진행중이거나, 가장 빠르거나
        Lecture objectiveLecture = null;

        boolean isStartMarginLecture = false;
        boolean isInProgressLecture = false;


        for (Lecture lecture : lecturesInRoom) {
            ZonedDateTime lectureStartTime = DateTimeUtilsForTest.getLectureStartTime(lecture);
            ZonedDateTime lectureEndTime = DateTimeUtilsForTest.getLectureEndTime(lecture);

            boolean isStartWithStartMarginTime = startMargin.isAfter(lectureStartTime);
            boolean isFinish = currentTime.isAfter(lectureEndTime);
            boolean isInProgress = currentTime.isAfter(lectureStartTime) && currentTime.isBefore(lectureEndTime);
            boolean isFinishWithFinishMarginTime = finishMargin.isAfter(lectureEndTime);
            boolean isEarliestThanTempTime = lectureStartTime.isBefore(tempTime);

            //끝났으면 continue
            if (isFinish) {
                continue;
            }

            //진행중이고 지정한 시간내 수업이 끝난다면
            if (isInProgress) {
                isInProgressLecture = true;
                if (isFinishWithFinishMarginTime) {
                    inProgressLectureEndTime = lectureEndTime;
                }
                continue;
            }

            if (isStartWithStartMarginTime) {
                isStartMarginLecture = true;
                objectiveLecture = lecture;
                break;
            }

            if (isEarliestThanTempTime) {
                objectiveLecture = lecture;
                tempTime = lectureStartTime;
            }
        }

        return Optional.ofNullable(objectiveLecture != null ? new LectureInfoDto(objectiveLecture, isStartMarginLecture, isInProgressLecture, inProgressLectureEndTime) : null);
    }

    //건물이 주어졌을때 현재 시각, 지정한 시간, 요일을 가져와서 가능한 강의실 추출
    public List<LectureRoomDto> findAvailableLectureRoomsWithSetTime(Building building, int setMinutes, ZonedDateTime currentTime) {
        List<LectureRoom> lectureRooms = lectureRoomService.findAllByBuildingNum(building.getBuildingNum());

        //       현재 시간에서 세팅한 시간을 더해서 마진을 만든다
        ZonedDateTime startMargin = currentTime.plus(setMinutes, ChronoUnit.MINUTES);
        ZonedDateTime finishMargin = currentTime.plus(setMinutes, ChronoUnit.MINUTES);

//       현재요일 추출 테스트
        //int currentDayOfWeek = (DayOfWeek.from(LocalDate.now()).getValue())-1;
        int currentDayOfWeek = 0;

//       강의실 리스트 가져와서(LectureRoom pk로) 비어있는 강의실 확인 후 리스트화
        List<LectureRoomDto> availableLectureRooms = new ArrayList<>();


        for (LectureRoom lectureRoom : lectureRooms) {
            //List<LectureDto> lecturesInRoom = lectureService.findAllByLectureRoomId(lectureRoom.getId());
            List<Lecture> lecturesInRoom = lectureService.findAllByLectureRoomIdAndDayOfWeek(lectureRoom.getId(), currentDayOfWeek);

            Optional<LectureInfoDto> lectureInfoDtoOptional  = isLectureRoomAvailable(lecturesInRoom, currentTime, startMargin, finishMargin);


            lectureInfoDtoOptional.ifPresent(lectureInfoDto -> {
                LectureRoomDto updatedLectureRoomDto = LectureRoomDto.toDto(lectureRoom, lectureInfoDto);
                availableLectureRooms.add(updatedLectureRoomDto);
            });

            }


        return availableLectureRooms;

    }

    public List<BuildingResponseDto> findNearestBuildingsWithAvailableLectureRooms(double latitude, double longitude, int setMinutes) {
        List<Building> nearestBuildings = findNearestBuildings(latitude, longitude);
        List<BuildingResponseDto> buildingResponseDtoList = new ArrayList<>();

        //test
        ZonedDateTime currentTime = DateTimeUtilsForTest.getCurrentSeoulTime();
        int addedBuildingsCount = 0;

        for (Building building : nearestBuildings) {
            if (addedBuildingsCount >= 3) {
                break;
            }

            List<LectureRoomDto> availableLectureRooms = findAvailableLectureRoomsWithSetTime(building, setMinutes, currentTime);

            if (availableLectureRooms != null && !availableLectureRooms.isEmpty()) {
                BuildingResponseDto buildingResponseDto = BuildingResponseDto.toDto(building, availableLectureRooms);
                buildingResponseDtoList.add(buildingResponseDto);
                addedBuildingsCount++;
            }
        }

        return buildingResponseDtoList;
    }
}

