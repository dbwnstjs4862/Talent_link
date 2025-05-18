package com.talentlink.talentlink.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserViewController {

    @GetMapping("/register")
    public String showRegisterForm() {
        return "user/register-form";  // templates/user/register-form.html
    }
}
