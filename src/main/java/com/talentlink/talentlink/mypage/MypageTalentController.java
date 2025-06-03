package com.talentlink.talentlink.mypage;

import com.talentlink.talentlink.security.CustomUserDetails;
import com.talentlink.talentlink.talent.TalentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage/posts")
@RequiredArgsConstructor
public class MypageTalentController {

    private final TalentService talentService;

    @GetMapping
    public String showMyTalents(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        model.addAttribute("talents", talentService.findByUser(userDetails.getUser()));
        return "user/mypage-posts";
    }
}
