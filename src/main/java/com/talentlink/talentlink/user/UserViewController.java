package com.talentlink.talentlink.user;

import com.talentlink.talentlink.user.dto.UserRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserViewController {

    private final UserService userService;

    // ✅ 회원가입 폼
    @GetMapping("/register")
    public String showRegisterForm() {
        return "user/register-form";  // templates/user/register-form.html
    }

    // ✅ 회원가입 처리 + 자동 로그인
    @PostMapping("/register")
    public String processRegister(UserRequest request, HttpServletRequest httpRequest) {
        userService.register(request, httpRequest);  // ✅ 세션 저장을 위해 request 함께 전달
        return "redirect:/"; // 자동 로그인 후 홈으로 이동
    }

    // ✅ 로그인 폼
    @GetMapping("/login")
    public String showLoginForm() {
        return "user/login-form";  // templates/user/login-form.html
    }
}
