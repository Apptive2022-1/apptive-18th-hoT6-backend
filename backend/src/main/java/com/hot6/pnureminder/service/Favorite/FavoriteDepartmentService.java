package com.hot6.pnureminder.service.Favorite;

import com.hot6.pnureminder.dto.Favorite.FavoriteDepartmentAnnouncementDto;
import com.hot6.pnureminder.entity.Announcement;
import com.hot6.pnureminder.entity.Favorites.FavoriteDepartment;
import com.hot6.pnureminder.entity.Member;
import com.hot6.pnureminder.entity.Department;
import com.hot6.pnureminder.repository.CustomMajorAnnouncementRepository;
import com.hot6.pnureminder.repository.DepartmentRepository;
import com.hot6.pnureminder.repository.Favorites.FavoriteDepartmentRepository;
import com.hot6.pnureminder.service.DepartmentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteDepartmentService {

    private final FavoriteDepartmentRepository favoriteDepartmentRepository;
    private final CustomMajorAnnouncementRepository customMajorAnnouncementRepository;
    private final DepartmentService departmentService;

    public FavoriteDepartment saveFavorite(FavoriteDepartment favorite) {
        return favoriteDepartmentRepository.save(favorite);
    }

    public List<FavoriteDepartment> getFavorites(Member member) {
        return favoriteDepartmentRepository.findByMember(member);
    }

    public List<FavoriteDepartmentAnnouncementDto> getFavoriteDepartments(Member member) {
        List<FavoriteDepartment> favoriteDepartments = favoriteDepartmentRepository.findByMember(member);

        List<FavoriteDepartmentAnnouncementDto> favoriteDepartmentAnnouncementDtos = new ArrayList<>();

        for (FavoriteDepartment favoriteDepartment : favoriteDepartments) {
            String departmentName = favoriteDepartment.getDepartment().getName();
            Boolean isAlert = favoriteDepartment.isAlert();
            String keyword = favoriteDepartment.getKeyword();
            List<Announcement> announcements =
                    customMajorAnnouncementRepository.findAllAnnouncementsByDepartment(departmentName);
            FavoriteDepartmentAnnouncementDto dto =
                    new FavoriteDepartmentAnnouncementDto(departmentName, isAlert, keyword, announcements);
            favoriteDepartmentAnnouncementDtos.add(dto);
        }
        return favoriteDepartmentAnnouncementDtos;
    }

    public void toggleFavorite(Member member, String departmentName) {
        Department department = departmentService.getDepartmentByName(departmentName)
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));

        Optional<FavoriteDepartment> favoriteDepartmentOptional = favoriteDepartmentRepository.findByMemberAndDepartment(member, department);

        if (favoriteDepartmentOptional.isPresent()) {
            // 이미 즐겨찾기에 추가되어 있다면 삭제
            favoriteDepartmentRepository.delete(favoriteDepartmentOptional.get());
        } else {
            // 즐겨찾기에 없다면 추가
            FavoriteDepartment favoriteDepartment = new FavoriteDepartment();
            favoriteDepartment.setMember(member);
            favoriteDepartment.setDepartment(department);
            this.saveFavorite(favoriteDepartment);
        }
    }
}
