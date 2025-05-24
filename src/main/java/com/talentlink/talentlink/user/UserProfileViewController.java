package com.talentlink.talentlink.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class UserProfileViewController {

    private final UserService userService;

    @GetMapping("/users/{userId}")
    public String viewUserProfile(@PathVariable Long userId, Model model) {
        User target = userService.findById(userId);
        model.addAttribute("target", target);
        return "user/profile-view"; // => templates/user/profile.html
    }
}
