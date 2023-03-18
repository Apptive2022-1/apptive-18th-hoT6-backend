package com.hot6.pnureminder.service;

import com.hot6.pnureminder.Dto.UserDto;
import com.hot6.pnureminder.domain.user.User;
import com.hot6.pnureminder.function.RandomCodeGenerator;
import com.hot6.pnureminder.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User saveUser(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setSettingcode(RandomCodeGenerator.generate());
        return userRepository.save(user);
    }

    @Transactional
    public User InfoUser(String code){
        Optional<User> user = userRepository.findBySettingcode(code);
        if (user.isPresent()){
            return user.get();
        }
        throw new EntityNotFoundException("코드에 저장된 설정이 없습니다.");
    }
    @Transactional
    public User updateUser (String code, UserDto userDto) {
        Optional<User> userOptional = userRepository.findBySettingcode(code);
        if (!userOptional.isPresent()) {
            throw new EntityNotFoundException("코드가 존재하지 않습니다. 재확인 부탁드립니다");
        }
        User user = userOptional.get();
        user.setKeyword(userDto.getKeyword());
        user.setState(userDto.getState());

        return userRepository.save(user);
    }


}
