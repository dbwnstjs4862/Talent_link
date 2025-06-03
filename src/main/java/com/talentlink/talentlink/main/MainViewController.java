package com.talentlink.talentlink.main;

import com.talentlink.talentlink.talent.TalentService;
import com.talentlink.talentlink.talentrequest.TalentRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainViewController {

    private final TalentService talentService;
    private final TalentRequestService talentRequestService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("latestTalents", talentService.findLatestTalents(3));
        model.addAttribute("latestRequests", talentRequestService.findLatestRequests(3));
        return "index";
    }
}
