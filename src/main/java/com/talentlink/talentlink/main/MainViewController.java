package com.talentlink.talentlink.main;

import com.talentlink.talentlink.talent.TalentService;
import com.talentlink.talentlink.talentrequest.TalentRequestService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainViewController {

    private final TalentService talentService;
    private final TalentRequestService talentRequestService;

    public MainViewController(TalentService talentService, TalentRequestService talentRequestService) {
        this.talentService = talentService;
        this.talentRequestService = talentRequestService;
    }

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("🌐 홈 진입 시 인증 상태: " + auth);
        System.out.println("🌐 로그인됨? " + (auth != null && auth.isAuthenticated()));

        HttpSession session = request.getSession(false);
        if (session != null) {
            System.out.println("🌐 홈 화면 세션 ID: " + session.getId());
        } else {
            System.out.println("❌ 홈 화면 진입 시 세션 없음");
        }

        model.addAttribute("latestTalents", talentService.findLatestTalents(3));
        model.addAttribute("latestRequests", talentRequestService.findLatestRequests(3));
        return "index";
    }

}
