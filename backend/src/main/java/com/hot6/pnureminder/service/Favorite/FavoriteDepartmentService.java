package com.hot6.pnureminder.service.Favorite;

import com.hot6.pnureminder.dto.Favorite.FavoriteDepartmentAnnouncementDto;
import com.hot6.pnureminder.entity.Announcement;
import com.hot6.pnureminder.entity.Favorites.FavoriteDepartment;
import com.hot6.pnureminder.entity.Member;
import com.hot6.pnureminder.repository.CustomMajorAnnouncementRepository;
import com.hot6.pnureminder.repository.Favorites.FavoriteDepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteDepartmentService {

    private final FavoriteDepartmentRepository favoriteDepartmentRepository;
    private final CustomMajorAnnouncementRepository customMajorAnnouncementRepository;

    public List<FavoriteDepartment> getFavorites(Member member) {
        return favoriteDepartmentRepository.findByMember(member);
    }

    public List<FavoriteDepartmentAnnouncementDto> getFavoriteDepartments(Member member) {
        List<FavoriteDepartment> favoriteDepartments = favoriteDepartmentRepository.findByMember(member);

        List<FavoriteDepartmentAnnouncementDto> favoriteDepartmentAnnouncementDtos = new ArrayList<>();

        for (FavoriteDepartment favoriteDepartment : favoriteDepartments) {
            String departmentName = favoriteDepartment.getDepartment().getName();
            List<Announcement> announcements =
                    customMajorAnnouncementRepository.findAllAnnouncementsByDepartment(departmentName);
            FavoriteDepartmentAnnouncementDto dto =
                    new FavoriteDepartmentAnnouncementDto(favoriteDepartment, announcements);
            favoriteDepartmentAnnouncementDtos.add(dto);
        }
        return favoriteDepartmentAnnouncementDtos;
    }
}
