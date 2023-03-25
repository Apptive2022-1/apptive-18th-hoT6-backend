package com.hot6.pnureminder.domain.user;


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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class Member implements UserDetails {

    @Id
    @Column(unique = true, name= "pk")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column(updatable = false,unique = true,nullable = false)
    private String memberId;
    @Column(nullable = false)
    private String password;

    @Column(name = "state")
    private Integer state;
    //    1 : 대학 2: 대학원 3: 휴학 4: 신입생

    @Column(name = "keyword")
    private String keyword;

    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password=passwordEncoder.encode(password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
