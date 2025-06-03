package com.talentlink.talentlink.security;

import com.talentlink.talentlink.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws ServletException, IOException {
        // 로그인한 사용자 정보 가져오기
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        // ✅ 세션에 loginUser로 저장 (프로젝트 전반 기준에 맞춤)
        request.getSession().setAttribute("loginUser", user);

        // 원래 가려고 했던 페이지로 이동
        response.sendRedirect("/");
    }
}
