package com.hot6.pnureminder.repository;

import com.hot6.pnureminder.domain.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberId(String memberId);
    boolean existsMembersByMemberId(String memberId);
}