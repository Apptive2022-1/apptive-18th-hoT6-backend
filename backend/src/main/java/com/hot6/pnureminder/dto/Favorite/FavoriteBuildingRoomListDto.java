package com.hot6.pnureminder.dto.Favorite;

import com.hot6.pnureminder.dto.LectureRoomDto;
import com.hot6.pnureminder.entity.Announcement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteBuildingRoomListDto {
    private String buildingName;
    private List<LectureRoomDto> lectureRooms;
}
