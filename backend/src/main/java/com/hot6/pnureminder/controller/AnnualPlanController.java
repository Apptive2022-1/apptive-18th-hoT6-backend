package com.hot6.pnureminder.controller;

import com.hot6.pnureminder.service.AnnualPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/annualplan")
public class AnnualPlanController {

    private final AnnualPlanService annualPlanService;

    @GetMapping("/{email}")
    public
}
