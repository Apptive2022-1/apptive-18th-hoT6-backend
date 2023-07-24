package com.hot6.pnureminder.service;

import com.hot6.pnureminder.dto.LectureRoom.*;
import com.hot6.pnureminder.entity.*;
import com.hot6.pnureminder.repository.BuildingRepository;
import com.hot6.pnureminder.repository.LectureRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureRoomService {

    private final LectureRoomRepository lectureRoomRepository;
    private final BuildingRepository buildingRepository;


    public List<LectureRoom> findAllByBuildingNum(Integer buildingNum) {
        return lectureRoomRepository.findAllByBuildingNum(buildingNum);
    }

    /** 성능개선중 에러로 인한 롤백
    public List<RoomWithStartTimeDTO> getAvailableLectureRoomsAndLectures(LectureRoomRequestDto requestDto) {
        LocalTime currentTime = requestDto.getCurrentTime();
        LocalTime endOfOngoingLecture = currentTime.plusMinutes(requestDto.getSetTime());
        Integer dayOfWeek = requestDto.getDayOfWeek();

        List<Object[]> rawData = lectureRoomRepository.findAvailableLectureRoomsAndLectures(
                requestDto.getBuildingName(),
                dayOfWeek,
                endOfOngoingLecture,
                currentTime
        );

        Map<String, RoomWithStartTimeDTO> roomToStartTimeMap = new HashMap<>();

        for(Object[] row : rawData) {
            LectureRoom lectureRoom = (LectureRoom) row[0];
            Lecture lecture = (Lecture) row[1];
            String roomNum = lectureRoom.getRoomNum();

            if(!roomToStartTimeMap.containsKey(roomNum) ||
                    (lecture != null &&
                            (roomToStartTimeMap.get(roomNum).getStartTime() == null ||
                                    roomToStartTimeMap.get(roomNum).getStartTime().after(lecture.getStartTime())))) {
                roomToStartTimeMap.put(roomNum, new RoomWithStartTimeDTO(roomNum, lecture == null ? null : lecture.getStartTime()));
            }
        }

        return new ArrayList<>(roomToStartTimeMap.values());
    }


    @Transactional(readOnly = true)
    public BuildingResponseDto getAvailableRooms(LectureRoomRequestDto requestDto) {
        String buildingName = requestDto.getBuildingName();

        List<AvailableNowDto> availableNowRooms = findAvailableNowRooms(requestDto);
        List<AvailableSoonDto> availableSoonRooms = findAvailableSoonRooms(requestDto);

        Building building = buildingRepository.findByBuildingName(buildingName)
                .orElseThrow(()->new RuntimeException("건물이 존재하지 않습니다."));

        return new BuildingResponseDto(
                buildingName,
                availableNowRooms,
                availableSoonRooms,
                building.getBuildingLat(),
                building.getBuildingLng()
        );
    }

    private List<AvailableNowDto> findAvailableNowRooms(LectureRoomRequestDto requestDto) {
        QLectureRoom qLectureRoom = QLectureRoom.lectureRoom;
        QLecture qLecture = QLecture.lecture;
        QBuilding qBuilding = QBuilding.building;

        LocalTime currentTime = requestDto.getCurrentTime();

        return queryFactory
                .select(Projections.constructor(AvailableNowDto.class,
                        qLectureRoom.roomNum,
                        qLecture.startTime))
                .from(qLectureRoom)
                .leftJoin(qLecture).on(qLectureRoom.eq(qLecture.lectureRoom)
                        .and(qLecture.dayOfWeek.eq(requestDto.getDayOfWeek())))
                .where(qLectureRoom.building.eq(qBuilding)
                        .and(qLecture.isNull()
                                .or(qLecture.startTime.after(Time.valueOf(currentTime))))
                )
                .orderBy(qLecture.startTime.asc())
                .fetch();
    }



    private List<AvailableSoonDto> findAvailableSoonRooms(LectureRoomRequestDto requestDto) {
        QLectureRoom qLectureRoom = QLectureRoom.lectureRoom;
        QLecture qLecture = QLecture.lecture;
        QBuilding qBuilding = QBuilding.building;

        LocalTime currentTime = requestDto.getCurrentTime();
        LocalTime endOfWaitTime = currentTime.plusMinutes(15);
        LocalTime setTime = currentTime.plusMinutes(requestDto.getSetTime());

        List<AvailableSoonDto> availableSoonRooms = queryFactory
                .select(Projections.constructor(AvailableSoonDto.class,
                        qLectureRoom.roomNum,
                        Expressions.stringTemplate("TIME_FORMAT(ADDTIME({0}, SEC_TO_TIME(TIME_TO_SEC({1}))), '%H:%i:%s')", qLecture.startTime, qLecture.runTime).as("nextClassStartTime")
                ))
                .from(qLectureRoom)
                .leftJoin(qLecture).on(qLectureRoom.eq(qLecture.lectureRoom)
                        .and(qLecture.dayOfWeek.eq(requestDto.getDayOfWeek()))
                        .and(qLecture.startTime.between(Time.valueOf(currentTime), Time.valueOf(endOfWaitTime))))
                .where(qLectureRoom.building.eq(qBuilding)
                        .and(Expressions.stringTemplate("TIME_FORMAT(ADDTIME({0}, {1}), '%H:%i:%s')", qLecture.startTime, qLecture.runTime).loe(setTime.toString()))
                        .and(JPAExpressions
                                .selectFrom(qLecture)
                                .where(qLecture.lectureRoom.eq(qLectureRoom)
                                        .and(qLecture.dayOfWeek.eq(requestDto.getDayOfWeek()))
                                        .and(qLecture.startTime.between(Time.valueOf(endOfWaitTime), Time.valueOf(setTime)))
                                ).notExists())
                )
                .orderBy(qLecture.startTime.asc())
                .fetch();

        return availableSoonRooms;
    }

    **/


    public List<AvailableNowDto> getNowAvailableLectureRoomsAndLectures(LectureRoomRequestDto requestDto) {
        LocalTime currentTime = requestDto.getCurrentTime();
        LocalTime endOfUsingTime = currentTime.plusMinutes(requestDto.getSetTime());

        List<Object[]> rawResults = lectureRoomRepository.findAvailableLectureRoomsAndLectures(
                requestDto.getBuildingName(),
                requestDto.getDayOfWeek(),
                endOfUsingTime,
                currentTime
        );



        return rawResults.stream()
                .map(result -> {
                    LectureRoom lectureRoom = (LectureRoom) result[0];
                    Lecture lecture = (result[1] != null) ? (Lecture) result[1] : null;
                    Time lectureStartTime = (lecture != null) ? lecture.getStartTime() : null;

                    return new AvailableNowDto(lectureRoom.getRoomNum(), lectureStartTime);
                })
                .collect(Collectors.toList());
    }
//
//    public List<AvailableSoonDto> getSoonAvailableLectureRoomsAndLectures(LectureRoomRequestDto requestDto) {
//        LocalTime currentTime = requestDto.getCurrentTime();
//        LocalTime endOfUsingTime = currentTime.plusMinutes(requestDto.getSetTime());
//        LocalTime endOfWaitTime = currentTime.plusMinutes(15);
//
//        List<Object[]> rawResults = lectureRoomRepository.findAvailableLectureRoomsWithinWaitTime(
//                requestDto.getBuildingName(),
//                requestDto.getDayOfWeek(),
//                endOfUsingTime,
//                currentTime,
//                endOfWaitTime
//        );
//
//        return rawResults.stream()
//                .filter(result -> result[1] != null)  // Ensure ongoing lecture exists
//                .map(result -> {
//                    LectureRoom lectureRoom = (LectureRoom) result[0];
//                    Lecture ongoingLecture = (Lecture) result[1];
//                    Lecture nextLecture = (result[2] != null) ? (Lecture) result[2] : null;
//
//                    // Calculate endTime of ongoingLecture
//                    LocalTime startTimeLocal = ongoingLecture.getStartTime().toLocalTime();
//                    LocalTime runTimeLocal = ongoingLecture.getRunTime().toLocalTime();
//                    LocalTime ongoingEndTimeLocal = startTimeLocal.plusHours(runTimeLocal.getHour())
//                            .plusMinutes(runTimeLocal.getMinute())
//                            .plusSeconds(runTimeLocal.getSecond());
//
//                    Time ongoingEndTime = Time.valueOf(ongoingEndTimeLocal);
//                    Time nextStartTime = (nextLecture != null) ? nextLecture.getStartTime() : null;
//
//                    return new AvailableSoonDto(lectureRoom.getRoomNum(), ongoingEndTime, nextStartTime);
//                })
//                .collect(Collectors.toList());
//    }


}
