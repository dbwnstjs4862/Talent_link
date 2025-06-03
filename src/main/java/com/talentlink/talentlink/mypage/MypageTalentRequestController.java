package com.talentlink.talentlink.mypage;

import com.talentlink.talentlink.security.CustomUserDetails;
import com.talentlink.talentlink.talentrequest.TalentRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage/talentrequests")
@RequiredArgsConstructor
public class MypageTalentRequestController {

    private final TalentRequestService talentRequestService;

    @GetMapping
    public String showMyTalentRequests(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        model.addAttribute("requests", talentRequestService.findByUser(userDetails.getUser()));
        return "user/mypage-talentrequests";
    }
}
