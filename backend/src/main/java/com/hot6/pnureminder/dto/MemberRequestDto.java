package com.hot6.pnureminder.dto;

import com.hot6.pnureminder.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {

    private String username;
    private String password;

    private Integer state;
    private String nickname;
    private Integer findQuesNum;
    private String findAnswer;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
            .username(username)
            .password(passwordEncoder.encode(password))
                .state(state)
                .nickname(nickname)
                .findQuesNum(findQuesNum)
                .findAnswer(findAnswer)
            .build();
    }


}
