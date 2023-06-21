package com.hot6.pnureminder.repository.Favorites;

import com.hot6.pnureminder.entity.Favorites.FavoriteDepartment;
import com.hot6.pnureminder.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteDepartmentRepository extends JpaRepository<FavoriteDepartment, Long> {
    List<FavoriteDepartment> findByMember(Member member);
}
