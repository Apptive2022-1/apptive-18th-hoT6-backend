package com.hot6.pnureminder.dto;

import com.hot6.pnureminder.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {
    private String email;

    private Integer state;



    public static MemberResponseDto of(Member member) {

        return new MemberResponseDto(
                member.getEmail(),
                member.getState()
        );
    }
}
