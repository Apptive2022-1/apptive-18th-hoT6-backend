package com.hot6.pnureminder.dto;

import com.hot6.pnureminder.entity.Announcement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementResponseDto {
    private String department;
    private List<Content> content;

    public static AnnouncementResponseDto toDto(String department, List<Announcement> announcements) {
        List<Content> contentList = announcements.stream()
                .map(Content::new)
                .collect(Collectors.toList());
        return new AnnouncementResponseDto(department, contentList);
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Content {
        private String id;
        private String title;
        private String urls;
        private String date;

        public Content(Announcement announcement) {
            this.id = announcement.getId();
            this.title = announcement.getTitle();
            this.urls = announcement.getUrls();
            this.date = announcement.getDate();
        }

    }
}
