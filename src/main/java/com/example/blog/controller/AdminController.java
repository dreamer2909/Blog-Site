package com.example.blog.controller;

import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public AdminController(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @ModelAttribute(name = "users")
    public List<User> users() {
        return userRepository.findAll();
    }

    @ModelAttribute(name = "posts")
    public List<Post> posts() {
        return postRepository.findAll();
    }

    @GetMapping()
    public String adminView() {
        return "admin";
    }

//    @PostMapping("/lock")
//    public String lockUser(@RequestParam String username) {
//        User user = userRepository.findUserByUsername(username);
//        userRepository.save(user);
//        return "redirect:/admin/user";
//    }

    @DeleteMapping("/post/delete/{id}")
    public String deletePost(@PathVariable long id) {
        postRepository.deleteById(id);
        return "redirect:/admin";
    }
}
