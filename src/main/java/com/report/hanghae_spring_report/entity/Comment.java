package com.report.hanghae_spring_report.entity;

import com.report.hanghae_spring_report.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @Column(name = "COMMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 여러 개의 댓글(Comment)은 하나의 게시글(Post)에 속한다"는 관계를 정의
     * @JoinColumn 어노테이션을 사용하여 데이터베이스 테이블 간의 외래키(Foreign key)를 지정
     * "POST_ID" 칼럼을 외래키로 사용
     */
    @ManyToOne
    @JoinColumn(name = "POST_ID", nullable = false)
    private Post post;

    /**
     * 여러 개의 댓글(Comment)은 하나의 회원(User)에 속한다"는 관계를 정의
     * @JoinColumn 어노테이션을 사용하여 데이터베이스 테이블 간의 외래키(Foreign key)를 지정
     * "USER_ID" 칼럼을 외래키로 사용
     */
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

//    @Column(nullable = false)
//    private String username;

    @Column(nullable = false)
    private String comment;

    public Comment(Post post, CommentRequestDto commentRequestDto, User user) {
        this.post = post;
        this.user = user;
//        this.username = user.getUsername();
        this.comment = commentRequestDto.getComment();
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.comment = commentRequestDto.getComment();
    }
}
