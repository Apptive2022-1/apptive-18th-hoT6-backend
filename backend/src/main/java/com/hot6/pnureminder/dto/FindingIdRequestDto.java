package com.hot6.pnureminder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindingIdRequestDto {
    private String nickname;
    private Integer findQuesNum;
    private String findAnswer;

    @Builder
    public FindingIdRequestDto(String nickname, Integer findQuesNum, String findAnswer) {
        this.nickname = nickname;
        this.findQuesNum = findQuesNum;
        this.findAnswer = findAnswer;
    }
}
