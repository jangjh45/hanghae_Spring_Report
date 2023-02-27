package com.report.hanghae_spring_report.entity;

import com.report.hanghae_spring_report.dto.PostDto;
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
    private String password;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String contents;


    public Post(PostDto postDto) {
        this.username = postDto.getUsername();
        this.password = postDto.getPassword();
        this.contents = postDto.getContents();
    }
}
