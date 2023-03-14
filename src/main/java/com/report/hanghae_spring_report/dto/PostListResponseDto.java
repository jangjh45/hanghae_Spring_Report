package com.report.hanghae_spring_report.dto;

import com.report.hanghae_spring_report.entity.Comment;
import com.report.hanghae_spring_report.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * PostListResponseDto 클래스는 Post 엔티티의 필드 중 일부만을 선택하여 DTO 객체에 담고 있습니다.
 * 이렇게 함으로써 필요한 정보만을 전달할 수 있고, 불필요한 정보는 생략할 수 있습니다.
 *
 * PostListResponseDto 클래스의 생성자는 Post 객체를 인자로 받습니다.
 * 이 생성자에서는 Post 객체에서 필요한 정보를 추출하여 해당 정보를 DTO 객체에 저장합니다.
 * Post 객체에서는 Comment 리스트를 가지고 있으므로,
 * 이를 순회하면서 CommentResponseDto 객체를 생성하여 commentList 필드에 추가합니다.
 *
 * 이렇게 생성된 PostListResponseDto 객체는 HTTP 응답 메시지의 본문에 담겨서 클라이언트로 전송됩니다.
 * 클라이언트는 이 정보를 사용하여 Post를 화면에 표시하거나, 다른 작업을 수행할 수 있습니다.
 */
@Getter
public class PostListResponseDto {
    private Long id;
    private String username;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> commentList = new ArrayList<>();

    public PostListResponseDto(Post post) {
        this.id = post.getId();
        this.username = post.getUser().getUsername();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        for (Comment comment : post.getCommentList()) {
            commentList.add(new CommentResponseDto(comment));
        }
    }
}
