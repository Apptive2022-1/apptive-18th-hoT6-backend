package com.hot6.pnureminder.dto.LectureRoom;

import com.hot6.pnureminder.entity.Building;
import com.hot6.pnureminder.entity.Lecture;
import com.hot6.pnureminder.entity.LectureRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class LectureRoomWithLecturesResponse {

    private Integer roomId;
    private String roomNum;
    private Integer buildingNum;
    private Building building;
    private List<LectureResponse> lectures;

    public LectureRoomWithLecturesResponse(LectureRoom lectureRoom, Lecture lecture) {
        this.roomId = lectureRoom.getId();
        this.roomNum = lectureRoom.getRoomNum();
        this.buildingNum = lectureRoom.getBuildingNum();
        this.building = lectureRoom.getBuilding();

        this.lectures = (lecture != null) ? Arrays.asList(new LectureResponse(lecture)) : new ArrayList<>();
    }

    @Data
    public static class LectureResponse {
        private Integer id;
        private Time startTime;
        private Time runTime;
        private Integer dayOfWeek;

        public LectureResponse(Lecture lecture) {
            this.id = lecture.getId();
            this.startTime = lecture.getStartTime();
            this.runTime = lecture.getRunTime();
            this.dayOfWeek = lecture.getDayOfWeek();
        }
    }
}

