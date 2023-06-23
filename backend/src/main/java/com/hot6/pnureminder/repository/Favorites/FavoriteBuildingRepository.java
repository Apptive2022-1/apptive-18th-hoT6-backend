package com.hot6.pnureminder.repository.Favorites;

import com.hot6.pnureminder.entity.Building;
import com.hot6.pnureminder.entity.Favorites.FavoriteBuilding;
import com.hot6.pnureminder.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteBuildingRepository extends JpaRepository<FavoriteBuilding, Long> {
    List<FavoriteBuilding> findByMember(Member member);

    Optional<FavoriteBuilding> findByMemberAndBuilding(Member member, Building building);

}
