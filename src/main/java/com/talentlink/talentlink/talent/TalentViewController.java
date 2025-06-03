package com.talentlink.talentlink.talent;

import com.talentlink.talentlink.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/talents")
public class TalentViewController {

    private final TalentService talentService;

    @GetMapping
    public String showList(Model model) {
        model.addAttribute("talents", talentService.findAll());
        return "talents/list";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "talents/register";
    }

    @PostMapping
    public String register(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam int price,
            HttpSession session
    ) {
        User loginUser = (User) session.getAttribute("loginUser");
        talentService.register(title, description, price, loginUser);
        return "redirect:/talents";
    }

    @GetMapping("/{id}")
    public String showDetail(@PathVariable Long id, Model model) {
        model.addAttribute("talent", talentService.findById(id));
        return "talents/detail";
    }
}
