package com.report.hanghae_spring_report.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.report.hanghae_spring_report.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity // DB 테이블과 매핑 대상
@NoArgsConstructor
public class Post extends Timestamped{

    @Id
    @Column(name = "POST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    public Post(PostRequestDto postRequestDto, User user) {
        this.username = user.getUsername();
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
        this.user = user;
    }

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
    }
}
