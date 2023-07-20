package com.hot6.pnureminder.dto;

import com.hot6.pnureminder.entity.LectureRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class LectureRoomDto {
    private Integer id;
    private String roomNum;
    private List<LectureDto> lectures;
    private LectureInfoDto earliestLectureInfoDto;



    public static LectureRoomDto toDto(LectureRoom lectureRoom, LectureInfoDto earliestLectureInfoDto) {
        return new LectureRoomDto(
                lectureRoom.getId(),
                lectureRoom.getRoomNum(),
                null,
                earliestLectureInfoDto
        );
    }

    // 기본 생성자는 private로 설정하여 외부에서 호출되지 않도록 한다.
    private LectureRoomDto(Integer id, String roomNum, List<LectureDto> lectures, LectureInfoDto earliestLectureInfoDto) {
        this.id = id;
        this.roomNum = roomNum;
        this.lectures = lectures;
        this.earliestLectureInfoDto = earliestLectureInfoDto;
    }


}
