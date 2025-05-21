package com.talentlink.talentlink.user;

import com.talentlink.talentlink.security.CustomUserDetails;
import com.talentlink.talentlink.talent.TalentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final TalentService talentService;

    @GetMapping
    public String showMypageHome() {
        return "user/mypage";
    }

    @GetMapping("/posts")
    public String showMyTalents(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        model.addAttribute("talents", talentService.findMyTalents(userDetails.getUser()));
        return "user/mypage-posts";
    }
}
