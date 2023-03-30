package com.hot6.pnureminder.dto;

import com.hot6.pnureminder.entity.Authority;
import com.hot6.pnureminder.entity.Member;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {

    private String email;
    private String password;

    private String nickname;

    private Integer findQuesNum;
    private String findAnswer;
    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
            .email(email)
            .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .findQuesNum(findQuesNum)
                .findAnswer(findAnswer)
            .authority(Authority.ROLE_USER)
            .build();
    }


}
