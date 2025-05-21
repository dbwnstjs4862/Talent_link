package com.talentlink.talentlink.talent;

import com.talentlink.talentlink.security.CustomUserDetails;
import com.talentlink.talentlink.talent.dto.TalentRequest;
import com.talentlink.talentlink.talent.dto.TalentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TalentViewController {

    private final TalentService talentService;

    @GetMapping("/talents")
    public String showAllTalents(Model model) {
        model.addAttribute("talents", talentService.findAllTalents());
        return "talents/list";
    }

    @GetMapping("/talents/register")
    public String showRegisterForm() {
        return "talents/register";
    }

    @PostMapping("/talents")
    public String registerTalentForm(@ModelAttribute TalentRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        talentService.registerTalent(request, userDetails.getUser());
        return "redirect:/talents";
    }

    @GetMapping("/talents/{id}")
    public String showTalentDetail(@PathVariable Long id, Model model) {
        model.addAttribute("talent", talentService.findById(id));
        return "talents/detail";
    }
}
