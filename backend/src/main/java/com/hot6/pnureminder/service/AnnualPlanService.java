package com.hot6.pnureminder.service;

import com.hot6.pnureminder.entity.AnnualPlan;
import com.hot6.pnureminder.repository.AnnualPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnualPlanService {

    private final AnnualPlanRepository annualPlanRepository;

    public List<AnnualPlan> getAnnualPlanByState(String state) {
        return annualPlanRepository.findByState(state);
    }

}
