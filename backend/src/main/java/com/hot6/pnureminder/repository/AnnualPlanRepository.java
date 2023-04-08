package com.hot6.pnureminder.repository;

import com.hot6.pnureminder.entity.AnnualPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnualPlanRepository extends JpaRepository<AnnualPlan, Long> {
    List<AnnualPlan> findAllByStateOrState (Integer state1, Integer state2);


}
