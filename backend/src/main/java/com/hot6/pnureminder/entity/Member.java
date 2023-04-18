package com.hot6.pnureminder.entity;

import jakarta.persistence.*;
import lombok.*;
<<<<<<< Updated upstream
=======
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
>>>>>>> Stashed changes

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
<<<<<<< Updated upstream
@Table(name = "reminderusers")
public class Member {

=======

//local test용
@Table(name = "remindersers")
public class Member implements UserDetails {
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
    @Builder
    public Member(String email, String password, String nickname,Integer findQuesNum, String findAnswer, Authority authority){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.findQuesNum = findQuesNum;
        this.findAnswer = findAnswer;
        this.authority = authority;
=======
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))

    private Set<Role> roles = new HashSet<>(Collections.singleton(Role.ROLE_USER));

//    유저가 가지고 있는 Role 객체를 이용하여 SimpleGrantedAuthority 객체를 생성하고, Stream을 이용하여 Collection 형태로 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
//    Collectors.toList() 메서드를 이용하여 반환되는 Stream 객체를 List 형태로 변환
                .collect(Collectors.toList());
>>>>>>> Stashed changes
    }
}
