package com.example.blog.controller;

import com.example.blog.Service.AuthenticationService;
import com.example.blog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private AuthenticationService authenticationService;

    @ModelAttribute(name = "loggedIn")
    public boolean isLoggedIn() {
        return authenticationService.isLoggedIn();
    }

    @GetMapping
    public String home(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "home";
    }
}
