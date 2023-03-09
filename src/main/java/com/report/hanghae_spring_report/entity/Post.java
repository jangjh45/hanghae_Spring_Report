package com.report.hanghae_spring_report.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.report.hanghae_spring_report.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity // DB 테이블과 매핑 대상
@NoArgsConstructor
public class Post extends Timestamped {

    @Id
    @Column(name = "POST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 여러 개의 게시글(Post)은 하나의 사용자(User)에 속한다"는 관계를 정의
     * @JoinColumn 어노테이션을 사용하여 데이터베이스 테이블 간의 외래키(Foreign key)를 지정
     * "USER_ID" 칼럼을 외래키로 사용
     */
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    /**
     * 게시글(Post)은 여러 개의 댓글(Comment)을 가질 수 있다"는 관계를 정의
     * Post 엔티티 클래스에 "commentList" 라는 List 타입의 필드를 정의
     * 각각의 필드가 "post" 필드와 연결되어 있음을 나타낸다.
     * Post 엔티티의 인스턴스가 생성될 때, 연결된 Comment 엔티티의 인스턴스도 함께 생성
     *
     * 게시글이 삭제될 때 해당 게시글에 연관된 댓글들도 함께 삭제하려면,
     * @OneToMany 어노테이션의 cascade 속성을 설정해야 합니다.
     */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @OrderBy(value = "createdAt DESC")
    List<Comment> commentList = new ArrayList<>();

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
