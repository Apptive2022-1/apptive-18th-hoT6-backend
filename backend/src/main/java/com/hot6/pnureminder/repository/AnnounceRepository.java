package com.hot6.pnureminder.repository;


import com.hot6.pnureminder.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnounceRepository extends JpaRepository<Announcement, Long> {

//    유저의 권한 정보에서 유저 id 를 통해 키워드 저장 db 를 member_id 로 서칭하여 키워드를 가져오게 한다.
    List<Announcement> findAllById (Long id);
}
