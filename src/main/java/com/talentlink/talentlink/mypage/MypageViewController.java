package com.talentlink.talentlink.mypage;

import com.talentlink.talentlink.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MypageViewController {

    @GetMapping("/mypage")
    public String mypage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        model.addAttribute("user", userDetails.getUser());
        return "user/mypage"; // templates/user/mypage.html
    }
}
