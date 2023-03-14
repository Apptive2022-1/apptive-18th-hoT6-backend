package com.hot6.pnureminder.service;

import com.hot6.pnureminder.Dto.UserDto;
import com.hot6.pnureminder.domain.user.User;
import com.hot6.pnureminder.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public Long save(User user) {
        userRepository.save(user);
        return user.getId();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }


}
