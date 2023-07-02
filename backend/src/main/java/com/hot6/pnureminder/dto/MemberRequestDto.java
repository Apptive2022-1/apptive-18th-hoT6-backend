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


    //변환 메서드는 역시 따로 매퍼 클래스를 만들어서 구현하는것이 좋은지
    //DTO에는 오직 로직을 넣지않고 데이터전송에만 사용해야할지
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
