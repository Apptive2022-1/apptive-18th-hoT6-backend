package com.hot6.pnureminder.controller;

import com.hot6.pnureminder.repository.UserRepository;
import com.hot6.pnureminder.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

//(순환 참조 에러, 객체 불변성, 테스트코드 작성시 편리성을 위해 생성자 주입 방식 적용)
//의존성 주입 실수 방지를 위해 final 키워드 사용
//
    private final UserRepository userRepository;
    private final UserService userService;


    @PostMapping("/usercreate")
    public ResponseEntity<User> createUser (@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.saveUser(userDto));
    }

    @GetMapping("/user/{code}")
    public ResponseEntity<User> getUser(@PathVariable String code) {
        return ResponseEntity.ok(userService.InfoUser(code));
    }
    @PutMapping("/user/{code}")
    public ResponseEntity<User> updateUser(@RequestBody UserDto userDto, @PathVariable String code) {
        return ResponseEntity.ok(userService.updateUser(code,userDto));
    }
}
