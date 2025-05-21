package com.talentlink.talentlink.user;

import com.talentlink.talentlink.user.dto.LoginRequest;
import com.talentlink.talentlink.user.dto.LoginResponse;
import com.talentlink.talentlink.user.dto.UserRequest;
import com.talentlink.talentlink.user.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입 - form 방식이므로 @RequestBody 제거
    @PostMapping
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest request) {
        UserResponse response = userService.registerWithoutAutoLogin(request);
        return ResponseEntity.ok(response);
    }
}
