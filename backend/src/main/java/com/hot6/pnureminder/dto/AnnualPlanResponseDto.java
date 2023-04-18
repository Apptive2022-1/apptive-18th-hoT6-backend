package com.hot6.pnureminder.dto;

import com.hot6.pnureminder.entity.AnnualPlan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AnnualPlanResponseDto {

    private String date;
    private String context;

    public static AnnualPlanResponseDto toDto(AnnualPlan annualPlan){

        return new AnnualPlanResponseDto(
                annualPlan.getDate(),
                annualPlan.getContext()
        );

    }

}
