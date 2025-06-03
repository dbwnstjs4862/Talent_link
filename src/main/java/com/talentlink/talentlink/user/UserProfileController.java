package com.talentlink.talentlink.user;

import com.talentlink.talentlink.security.CustomUserDetails;
import com.talentlink.talentlink.user.dto.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserProfileController {

    private final UserService userService;

    // ✅ 내 정보 보기
    @GetMapping("/mypage/profile")
    public String showMyProfile(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        model.addAttribute("user", userDetails.getUser());
        return "user/profile";
    }

    // ✅ 내 정보 수정 폼
    @GetMapping("/mypage/profile/edit")
    public String editMyProfileForm(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        model.addAttribute("user", userDetails.getUser());
        return "user/edit-profile";
    }

    // ✅ 내 정보 수정 처리
    @PostMapping("/mypage/profile/edit")
    public String updateMyProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute UserUpdateRequest request
    ) {
        userService.updateProfile(userDetails.getUser(), request);
        return "redirect:/mypage/profile";
    }

    // ✅ 다른 사용자 프로필 보기 → /users/{userId}
    @GetMapping("/users/{userId}")
    public String viewUserProfile(@PathVariable Long userId,
                                  @AuthenticationPrincipal CustomUserDetails userDetails,
                                  Model model) {
        if (userId.equals(userDetails.getUser().getId())) {
            return "redirect:/mypage/profile";
        }

        User target = userService.findById(userId);
        model.addAttribute("target", target);
        return "user/profile-view";
    }
}
