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

    // 재능 등록 폼 페이지
    @GetMapping("/talents/register")
    public String showRegisterForm() {
        return "talents/register"; // templates/talents/register.html
    }

    // 재능 등록 처리
    @PostMapping("/talents")
    public String registerTalentForm(
            @ModelAttribute TalentRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        talentService.registerTalent(request, userDetails.getUser());
        return "redirect:/talents"; // 등록 후 전체 목록으로 이동
    }

    // 전체 재능 목록
    @GetMapping("/talents")
    public String showAllTalents(Model model) {
        List<TalentResponse> talents = talentService.findAllTalents();
        model.addAttribute("talents", talents);
        return "talents/list"; // templates/talents/list.html
    }

    // 재능 상세 보기
    @GetMapping("/talents/{id}")
    public String showTalentDetail(
            @PathVariable Long id,
            Model model
    ) {
        TalentResponse talent = talentService.findById(id);
        model.addAttribute("talent", talent);
        return "talents/detail"; // templates/talents/detail.html
    }
}
