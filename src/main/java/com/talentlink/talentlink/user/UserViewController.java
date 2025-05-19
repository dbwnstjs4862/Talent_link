package com.talentlink.talentlink.user;

import com.talentlink.talentlink.security.CustomUserDetails;
import com.talentlink.talentlink.talent.TalentService;
import com.talentlink.talentlink.talent.dto.TalentResponse;
import com.talentlink.talentlink.user.dto.UserUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserViewController {
    @Autowired
    private TalentService talentService;
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "user/register-form";  // templates/user/register-form.html
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "user/login-form";  // templates/user/login-form.html
    }

    // ✅ 마이페이지 홈
    @GetMapping("/mypage")
    public String showMypageHome() {
        return "user/mypage";
    }

    // ✅ 내가 올린 글 목록
    @GetMapping("/mypage/posts")
    public String showMyTalents(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model
    ) {
        List<TalentResponse> myTalents = talentService.findMyTalents(userDetails.getUser());
        model.addAttribute("talents", myTalents);
        return "user/mypage-posts";
    }

    // ✅ 내 정보 보기
    @GetMapping("/mypage/profile")
    public String showMyProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model
    ) {
        model.addAttribute("user", userDetails.getUser());
        return "user/profile";
    }

    // ✅ 내 정보 수정 폼
    @GetMapping("/mypage/profile/edit")
    public String editMyProfileForm(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model
    ) {
        model.addAttribute("user", userDetails.getUser());
        return "user/edit-profile";
    }

    // ✅ 내 정보 수정 처리
    @PostMapping("/mypage/profile/edit")
    public String updateMyProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute UserUpdateRequest request
    ) {
        userService.updateProfile(userDetails.getUser(), request); // ✅ 서비스에 위임
        return "redirect:/mypage/profile";
    }
}