package com.hot6.pnureminder.dto.LectureRoom;

import com.hot6.pnureminder.entity.Building;
import com.hot6.pnureminder.entity.Lecture;
import com.hot6.pnureminder.entity.LectureRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

        // 아래 로직은 실제로는 여러 강의 정보를 수집하는 데 필요한 추가적인 처리를 포함해야 할 수 있습니다.
        this.lectures = new ArrayList<>();
        if(lecture != null) {
            this.lectures.add(new LectureResponse(lecture));
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class LectureResponse {
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
