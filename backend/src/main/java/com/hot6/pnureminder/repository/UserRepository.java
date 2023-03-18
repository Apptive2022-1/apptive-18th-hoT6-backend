package com.hot6.pnureminder.repository;

import com.hot6.pnureminder.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    SQL 구문에서 User 객체가 저장된 DB에서 WHERE 조건으로 settingcode를 이용하였다.
//    스프링부트에서는 메서드명을 통해 컬럼,(And,Or,Between)등으로 데이터 조회가 가능하다.
    Optional<User> findBySettingcode(String settingcode);

    Optional<User> findById(Long id);

}