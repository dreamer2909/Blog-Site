package com.example.blog.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @Column(length = 200000)
    private String content;
    @Column(length = 10000)
    private String summary;
    private Date createdAt = new Date();
    @Lob
    private String postImage;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

//    @ManyToOne
//    @JoinColumn(name = "series_id")
//    private Series series;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostComment> comments = new ArrayList<>();

    public String getTimeString() {
        String s = new SimpleDateFormat("HH:mm dd/MM/yyyy").format(createdAt);
        return s;
    }

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
