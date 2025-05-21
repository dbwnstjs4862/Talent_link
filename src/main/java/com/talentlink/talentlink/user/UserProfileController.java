package com.talentlink.talentlink.user;

import com.talentlink.talentlink.security.CustomUserDetails;
import com.talentlink.talentlink.user.dto.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mypage/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserService userService;

    // ✅ 내 정보 보기
    @GetMapping
    public String showMyProfile(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        model.addAttribute("user", userDetails.getUser());
        return "user/profile";
    }

    // ✅ 내 정보 수정 폼
    @GetMapping("/edit")
    public String editMyProfileForm(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        model.addAttribute("user", userDetails.getUser());
        return "user/edit-profile";
    }

    // ✅ 내 정보 수정 처리
    @PostMapping("/edit")
    public String updateMyProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute UserUpdateRequest request
    ) {
        userService.updateProfile(userDetails.getUser(), request);
        return "redirect:/mypage/profile";
    }
}
