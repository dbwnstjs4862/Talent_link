package com.talentlink.talentlink.user;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserViewController {

    private final UserService userService;

    // ✅ 회원가입 폼
    @GetMapping("/register")
    public String showRegisterForm() {
        return "user/register-form";
    }

    // ✅ 회원가입 처리 + 자동 로그인
    @PostMapping("/register")
    public String processRegister(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String nickname,
            HttpServletRequest httpRequest
    ) {
        userService.register(username, password, nickname, httpRequest);
        return "redirect:/";
    }

    // ✅ 로그인 폼
    @GetMapping("/login")
    public String showLoginForm() {
        return "user/login-form";
    }
}
