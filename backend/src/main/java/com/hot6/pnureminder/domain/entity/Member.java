package com.hot6.pnureminder.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Users")
public class Member {

    @Id
    @Column(unique = true, name= "pk")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

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

