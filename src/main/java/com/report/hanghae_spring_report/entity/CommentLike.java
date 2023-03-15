package com.report.hanghae_spring_report.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class CommentLike {

    @Id
    @Column(name = "COMMENT_LIKE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;


    public CommentLike(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
    }
}
