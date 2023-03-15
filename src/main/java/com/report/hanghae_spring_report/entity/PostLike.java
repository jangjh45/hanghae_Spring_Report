package com.report.hanghae_spring_report.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class PostLike {

    @Id
    @Column(name = "POST_LIKE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    public PostLike(User user, Post post) {
        this.user = user;
        this.post = post;

    }
}
