package com.hot6.pnureminder.controller;

import com.hot6.pnureminder.Dto.UserDto;
import com.hot6.pnureminder.domain.user.User;
import com.hot6.pnureminder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        user.setKeyword(user.getKeyword());
        // 회원 정보 저장
        userService.save(user);
        return ResponseEntity.ok("User created successfully");
    }
    @GetMapping("/members/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok().body(user);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
