package com.hot6.pnureminder.service;


import com.hot6.pnureminder.entity.Announcement;
import com.hot6.pnureminder.repository.CustomMajorAnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnnouncementService {
    private final CustomMajorAnnouncementRepository customMajorAnnouncementRepository;
//deprecated
    public List<Announcement> getAnnouncementsByDepartmentName(String departmentName) {
        return customMajorAnnouncementRepository.findAllAnnouncementsByDepartment(departmentName);
    }

    public List<Announcement> getAnnouncementsByDepartmentNameAndKeyword(String departmentName, String keyword) {
        List<Announcement> announcements = customMajorAnnouncementRepository.findAllAnnouncementsByDepartment(departmentName);

        // Keyword가 주어진 경우, 해당 keyword를 제목에 포함하는 공지사항만 필터링
        if (keyword != null && !keyword.isEmpty()) {
            announcements = announcements.stream()
                    .filter(announcement -> announcement.getTitle().contains(keyword))
                    .collect(Collectors.toList());
        }

        return announcements;
    }
}
