package com.talentlink.talentlink.talentrequest;

import com.talentlink.talentlink.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/talentrequests")
public class TalentRequestViewController {

    private final TalentRequestService talentRequestService;

    @GetMapping
    public String showList(Model model) {
        model.addAttribute("requests", talentRequestService.findAll());
        return "talentrequest/list";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "talentrequest/register";
    }

    @PostMapping
    public String register(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam int budget,
            @RequestParam("deadline") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deadline,
            HttpSession session
    ) {
        User loginUser = (User) session.getAttribute("loginUser");
        talentRequestService.register(title, description, budget, deadline, loginUser);
        return "redirect:/talentrequests";
    }


    @GetMapping("/{id}")
    public String showDetail(@PathVariable Long id, Model model) {
        model.addAttribute("request", talentRequestService.findById(id));
        return "talentrequest/detail";
    }
}
