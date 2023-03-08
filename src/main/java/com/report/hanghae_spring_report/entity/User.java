package com.report.hanghae_spring_report.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User 엔티티와 Post 엔티티 간의 관계를 양방향으로 설정한 경우,
 * User 엔티티에는 @OneToMany 어노테이션을 추가하여 Post 엔티티와의 관계를 정의하고,
 * Post 엔티티에는 @ManyToOne 어노테이션을 추가하여 User 엔티티와의 관계를 정의합니다.
 * 이 때, 양쪽 엔티티에서 모두 mappedBy 속성을 사용하여 관계의 주인을 지정해야 합니다.
 */
@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * nullable: null 허용 여부
     * nullable = false로 지정하여 해당 칼럼의 값이 null이 되지 않도록 합니다.
     * unique: 중복 허용 여부
     * false 일때 중복 허용
     */
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserEnum role;

    /**
     * 사용자(User)는 여러 개의 게시글(Post) 또는 댓글(Comment)을 가질 수 있다"는 관계를 정의
     * User 엔티티 클래스에 "postList"와 "commentList" 라는 두 개의 List 타입의 필드를 정의
     * 각각의 필드가 "user" 필드와 연결되어 있음을 나타낸다.
     * User 엔티티의 인스턴스가 생성될 때, 연결된 Post와 Comment 엔티티의 인스턴스도 함께 생성
     */
    @OneToMany(mappedBy = "user")
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> commentList = new ArrayList<>();

    public User(String username, String password, UserEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}

