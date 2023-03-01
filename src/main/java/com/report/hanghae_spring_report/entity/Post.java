package com.report.hanghae_spring_report.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.report.hanghae_spring_report.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;


    public Post(PostRequestDto postRequestDto) {
        this.username = postRequestDto.getUsername();
        this.password = postRequestDto.getPassword();
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
    }

    public void update(PostRequestDto postRequestDto) {
        this.username = postRequestDto.getUsername();
        this.password = postRequestDto.getPassword();
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
    }
}
