package com.hot6.pnureminder.domain.user;


import com.hot6.pnureminder.Dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "state")
    private Integer state;
    //    1 : 대학 2: 대학원 3: 휴학 4: 신입생
    @Column(name = "code")
    private String settingcode;

    @Column(name = "keyword")
    private String keyword;

    public void update(UserDto userDto) {
        this.keyword = userDto.getKeyword();
        this.state = userDto.getState();
    }
}

