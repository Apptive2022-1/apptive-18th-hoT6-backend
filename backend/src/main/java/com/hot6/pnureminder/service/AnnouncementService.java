package com.hot6.pnureminder.service;


import com.hot6.pnureminder.entity.Announcement;
import com.hot6.pnureminder.repository.CustomMajorAnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnnouncementService {
    private final CustomMajorAnnouncementRepository customMajorAnnouncementRepository;

    public List<Announcement> getAnnouncementsByDepartment(String department) {
        return customMajorAnnouncementRepository.findAllAnnouncementsByDepartment(department);
    }
}
