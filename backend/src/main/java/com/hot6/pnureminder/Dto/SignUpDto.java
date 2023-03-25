package com.hot6.pnureminder.Dto;

import com.hot6.pnureminder.domain.user.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {

    private String memberId;

    private String password;

    private String checkedPassword;

    @Builder
    public Member toEntity(String encodePwd){
        return Member.builder()
                .memberId(memberId)
                .password(encodePwd)
                .build();
    }

}
