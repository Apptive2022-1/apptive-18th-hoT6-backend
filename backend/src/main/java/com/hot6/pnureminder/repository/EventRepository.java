package com.hot6.pnureminder.repository;

import com.hot6.pnureminder.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {

    Optional<Event> findByMemberIdAndEventId(Long memberId, Long eventId);

    @Query("SELECT e FROM Event e WHERE e.member.id = :memberId AND " +
            "(e.startTime BETWEEN :start AND :end OR e.endTime BETWEEN :start AND :end)")
    List<Event> findAllEventsWithinMonthByMemberId(@Param("memberId") Long memberId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}