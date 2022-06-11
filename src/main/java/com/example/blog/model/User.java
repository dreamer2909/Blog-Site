package com.example.blog.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String displayName;
    private String password;
    private String gender;
    private Date dob;
    private String profile;
    private String job;
    private String education;
    private String location;
    private String hometown;
    private Date createdAt;

    @Lob
    private String avatar;

    @Lob
    private String backgroundImage;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new LinkedList<>();

    @OneToMany(mappedBy = "user")
    private List<PostComment> comments = new ArrayList<>();

    private boolean locked = false;

    public User(String username, String displayName, String gender, Date dob, String password) {
        this.username = username;
        this.displayName = displayName;
        this.gender = gender;
        this.dob = dob;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
