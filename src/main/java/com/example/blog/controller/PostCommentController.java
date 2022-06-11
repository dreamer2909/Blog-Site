package com.example.blog.controller;

import com.example.blog.model.Post;
import com.example.blog.model.PostComment;
import com.example.blog.model.User;
import com.example.blog.repository.PostCommentRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path = "/comment", produces = "text/plain;charset=UTF-8")
public class PostCommentController {
    private final PostCommentRepository postCommentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostCommentController(PostCommentRepository postCommentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.postCommentRepository = postCommentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/{title}")
    public String comment(@PathVariable String title, @AuthenticationPrincipal User user, @RequestParam String content, @RequestParam Long postId) throws UnsupportedEncodingException {
        PostComment comment = new PostComment();
        comment.setContent(content);
        comment.setUser(user);

        Post post = postRepository.getById(postId);
        comment.setPost(post);
        List<PostComment> comments = post.getComments();
        comments.add(comment);
        post.setComments(comments);
        post.setCreatedAt(new Date());

        User u = userRepository.getById(user.getId());
        List<PostComment> comments1 = u.getComments();
        comments1.add(comment);
        u.setComments(comments1);

        userRepository.save(u);
        postCommentRepository.save(comment);
        return "redirect:/post/" + URLEncoder.encode(title, StandardCharsets.UTF_8.name());
    }
}
