package com.example.blog.controller;

import com.example.blog.Service.AuthenticationService;
import com.example.blog.model.Category;
import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.repository.PostRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/post", produces = "text/plain;charset=UTF-8")
public class PostController {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final AuthenticationService authenticationService;

    @ModelAttribute(name = "user")
    public User getUser(@AuthenticationPrincipal User user) {
        return user;
    }

    @ModelAttribute(name = "loggedIn")
    public boolean isLoggedIn() {
        return authenticationService.isLoggedIn();
    }

    public PostController(PostRepository postRepository, CategoryRepository categoryRepository, AuthenticationService authenticationService) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/write")
    public String writingView() {
        return "editor";
    }

    @PostMapping("/write")
    public String post(@AuthenticationPrincipal User user, @RequestParam String categoryTitle,
                       @RequestParam MultipartFile postImage, @RequestParam String summary,
                       @RequestParam String title, @RequestParam String content) throws IOException {
        Post post = new Post();
        post.setUser(user);
        post.setTitle(title);
        post.setContent(content);
        post.setPostImage(Base64.getEncoder().encodeToString(postImage.getBytes()));
        post.setSummary(summary);
        post.setCreatedAt(new Date());
        Category category = categoryRepository.findCategoryByTitle(categoryTitle);
        post.setCategory(category);
        List<Post> posts = category.getPosts();
        posts.add(post);
        category.setPosts(posts);
        postRepository.save(post);
        return "redirect:/post/" + URLEncoder.encode(title, StandardCharsets.UTF_8.name());
    }
    
    @GetMapping("/{title}")
    public String view(@PathVariable String title, Model model) throws UnsupportedEncodingException {
        title = URLDecoder.decode(title, StandardCharsets.UTF_8.name());
        Post post = postRepository.findPostByTitle(title);
        model.addAttribute("post", post);
        return "post";
    }
}
