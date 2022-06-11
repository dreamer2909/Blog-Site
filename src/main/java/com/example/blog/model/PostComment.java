package com.example.blog.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor()
public class PostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private Date createdAt = new Date();

    public String getTimeAgo() {
        Date now = new Date();
        long second = (now.getTime() - createdAt.getTime()) / 1000;
        if (second < 60) return second + " giây trước";

        long minute = second / 60;
        if (minute < 60) return minute + " phút trước";

        long hour = minute / 60;
        if (hour < 24) return hour + " giờ trước";

        long day = hour / 24;
        if (day < 31) return day + " ngày trước";

        long month = day / 30;
        if (month < 12) return month + " tháng trước";

        return (month / 12) + " năm trước";
    }
}
