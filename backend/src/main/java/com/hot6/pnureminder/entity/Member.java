package com.hot6.pnureminder.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reminderusers")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(updatable = false,unique = true,nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @Column(name = "nickname")
    private String nickname;
    @Column(name = "findQuestion")
    private Integer findQuesNum;
    @Column(name = "findAnswer")
    private String findAnswer;

    @Enumerated(EnumType.STRING)
    @Column
    private Authority authority;

    @Builder
    public Member(String email, String password, String nickname,Integer findQuesNum, String findAnswer, Authority authority){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.findQuesNum = findQuesNum;
        this.findAnswer = findAnswer;
        this.authority = authority;
    }
}
