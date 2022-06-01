package com.example.blog.controller;

import com.example.blog.Service.AuthenticationService;
import com.example.blog.model.Category;
import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.repository.CategoryRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final AuthenticationService authenticationService;

    public CategoryController(CategoryRepository categoryRepository, AuthenticationService authenticationService) {
        this.categoryRepository = categoryRepository;
        this.authenticationService = authenticationService;
    }

    @ModelAttribute(name = "loggedIn")
    public boolean isLoggedIn() {
        return authenticationService.isLoggedIn();
    }

    @ModelAttribute(name = "user")
    public User getUser(@AuthenticationPrincipal User user) {
        return user;
    }

    @GetMapping("/{title}")
    public String debateView(Model model, @PathVariable String title) {
        Category category = new Category();
        switch (title) {
            case "debate":
                category = categoryRepository.findCategoryByTitle("QUAN ĐIỂM - TRANH LUẬN");
                break;
            case "sport":
                category = categoryRepository.findCategoryByTitle("THỂ THAO");
                break;
            case "game":
                category = categoryRepository.findCategoryByTitle("GAME");
                break;
            case "movie":
                category = categoryRepository.findCategoryByTitle("PHIM");
                break;
            case "book":
                category = categoryRepository.findCategoryByTitle("SÁCH");
                break;
            case "create":
                category = categoryRepository.findCategoryByTitle("SÁNG TÁC");
                break;
            case "music":
                category = categoryRepository.findCategoryByTitle("ÂM NHẠC");
                break;
            case "travel-food":
                category = categoryRepository.findCategoryByTitle("DU LỊCH - ẨM THỰC");
                break;
            case "science":
                category = categoryRepository.findCategoryByTitle("KHOA HỌC");
                break;
            case "skill":
                category = categoryRepository.findCategoryByTitle("KỸ NĂNG");
                break;
            default:
        }
        List<Post> posts = category.getPosts();
        model.addAttribute("posts", posts);
        return "category";
    }
}
