package com.talentlink.talentlink.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        Long id = userService.register(user);
        return ResponseEntity.ok("회원가입 성공! ID: " + id);
    }

    // 로그인 (간단 버전)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginUser) {
        return userService.findByUsername(loginUser.getUsername())
                .filter(user -> user.getPassword().equals(loginUser.getPassword()))
                .map(user -> ResponseEntity.ok("로그인 성공!"))
                .orElse(ResponseEntity.status(401).body("로그인 실패"));
    }
}
