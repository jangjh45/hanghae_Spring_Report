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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;


    public Post(PostRequestDto postRequestDto) {
        this.username = postRequestDto.getUsername();
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
    }

    public void update(PostRequestDto postRequestDto) {
        this.username = postRequestDto.getUsername();
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
    }
}
