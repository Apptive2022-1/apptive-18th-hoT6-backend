package com.hot6.pnureminder.dto;


import com.hot6.pnureminder.domain.entity.Member;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {

    private String email;

    public static MemberResponseDto of(Member member){
        return new MemberResponseDto(member.getEmail());
    }
}
