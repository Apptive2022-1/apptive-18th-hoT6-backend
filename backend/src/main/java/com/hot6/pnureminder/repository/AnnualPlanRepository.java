package com.hot6.pnureminder.repository;

import com.hot6.pnureminder.entity.AnnualPlan;
import com.hot6.pnureminder.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnnualPlanRepository extends JpaRepository<AnnualPlan, Long> {
    List<AnnualPlan> findAllByStateOrState (Integer state1, Integer state2);

    @Query("SELECT a FROM AnnualPlan a WHERE a.state = :state1 OR a.state = :state2 AND " +
            "(a.startDate BETWEEN :start AND :end OR a.endDate BETWEEN :start AND :end)")
    List<AnnualPlan> findAllAnnualPlanWithinMonthByStateOrState(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, Integer state1, Integer state2);


}
