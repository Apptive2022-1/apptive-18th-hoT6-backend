package com.hot6.pnureminder.dto.Favorite;

import com.hot6.pnureminder.entity.Announcement;
import com.hot6.pnureminder.entity.Favorites.FavoriteDepartment;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteDepartmentAnnouncementDto {
    private String departmentName;
    private boolean isAlert;
    private String keyword;
    private List<Announcement> announcements;
}
