package com.talentlink.talentlink.talentrequest;

import com.talentlink.talentlink.security.CustomUserDetails;
import com.talentlink.talentlink.talentrequest.dto.TalentRequestRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TalentRequestViewController {

    private final TalentRequestService requestService;

    @GetMapping("/talentrequests")
    public String showAllRequests(Model model) {
        model.addAttribute("requests", requestService.findAll());
        return "talentrequest/list";
    }

    @GetMapping("/talentrequests/register")
    public String showRegisterForm() {
        return "talentrequest/register";
    }

    @PostMapping("/talentrequests")
    public String registerRequest(@ModelAttribute TalentRequestRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        requestService.register(request, userDetails.getUser());
        return "redirect:/talentrequests";
    }

    @GetMapping("/talentrequests/{id}")
    public String showRequestDetail(@PathVariable Long id, Model model) {
        model.addAttribute("request", requestService.findById(id));
        return "talentrequest/detail";
    }

    // ✅ 마이페이지 - 내가 요청한 글
    @GetMapping("/mypage/talentrequests")
    public String showMyRequests(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        model.addAttribute("requests", requestService.findMyRequests(userDetails.getUser()));
        return "talentrequest/mypage-list";
    }
}

