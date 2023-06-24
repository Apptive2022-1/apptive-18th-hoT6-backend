package com.hot6.pnureminder.repository;

import com.hot6.pnureminder.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {

    Optional<Event> findByMemberIdAndEventId(Long memberId, Long eventId);

}
