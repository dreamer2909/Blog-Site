package com.example.blog.controller;

import com.example.blog.Service.AuthenticationService;
import com.example.blog.model.User;
import com.example.blog.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, AuthenticationService authenticationService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
        this.passwordEncoder = passwordEncoder;
    }

    @ModelAttribute(name = "loggedIn")
    public boolean isLoggedIn() {
        return authenticationService.isLoggedIn();
    }

    @GetMapping()
    public String redirectUserPage(@AuthenticationPrincipal User user) {
        return "redirect:/user/" + user.getUsername();
    }

    @GetMapping("/{username}")
    public String userPage(@PathVariable String username, Model model) {
        User u = userRepository.findUserByUsername(username);
        model.addAttribute("user", u);
        return "user";
    }

    @GetMapping("/setting")
    public String settingView(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        String[] arr = new SimpleDateFormat("dd/MM/yyyy").format(user.getDob()).split("/");
        int day = Integer.parseInt(arr[0]);
        int month = Integer.parseInt(arr[1]);
        int year = Integer.parseInt(arr[2]);
        model.addAttribute("day", day);
        model.addAttribute("month", month);
        model.addAttribute("year", year);
        return "setting";
    }

    @PostMapping("/change-password")
    public String changePassword(@AuthenticationPrincipal User user, @RequestParam String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return "redirect:/user/setting";
    }

    @PostMapping("/setting/account")
    public String userSetting(@AuthenticationPrincipal User user, @RequestParam String displayName,
                              @RequestParam String gender, @RequestParam String day, @RequestParam String month,
                              @RequestParam String year, @RequestParam String profile, @RequestParam MultipartFile avatar,
                              @RequestParam MultipartFile backgroundImage) throws ParseException, IOException {
        user.setDisplayName(displayName);
        user.setGender(gender);
        while (day.length() < 2) day = "0" + day;
        while (month.length() < 2) month = "0" + month;
        Date d = new SimpleDateFormat("dd/MM/yyyy").parse( day + "/" + month + "/" + year);
        user.setDob(d);
        user.setProfile(profile);
        user.setAvatar(Base64.getEncoder().encodeToString(avatar.getBytes()));
        user.setBackgroundImage(Base64.getEncoder().encodeToString(backgroundImage.getBytes()));
        userRepository.save(user);
        return "redirect:/user/setting";
    }

    @PostMapping("/setting/story")
    public String userSetting(@AuthenticationPrincipal User user, @RequestParam String job,
                              @RequestParam String education, @RequestParam String location,
                              @RequestParam String hometown) {
        user.setJob(job);
        user.setEducation(education);
        user.setLocation(location);
        user.setHometown(hometown);
        userRepository.save(user);
        return "redirect:/user/setting";
    }

}
