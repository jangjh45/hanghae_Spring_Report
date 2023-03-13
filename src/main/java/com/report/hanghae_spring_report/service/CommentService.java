package com.report.hanghae_spring_report.service;

import com.report.hanghae_spring_report.common.ApiException;
import com.report.hanghae_spring_report.common.ExceptionEnum;
import com.report.hanghae_spring_report.dto.CommentRequestDto;
import com.report.hanghae_spring_report.dto.CommentResponseDto;
import com.report.hanghae_spring_report.dto.MessageResponse;
import com.report.hanghae_spring_report.dto.StatusEnum;
import com.report.hanghae_spring_report.entity.Comment;
import com.report.hanghae_spring_report.entity.Post;
import com.report.hanghae_spring_report.entity.User;
import com.report.hanghae_spring_report.entity.UserRoleEnum;
import com.report.hanghae_spring_report.jwt.JwtUtil;
import com.report.hanghae_spring_report.repository.CommentRepository;
import com.report.hanghae_spring_report.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;

    // 관리자 계정만 모든 댓글 수정, 삭제 가능
    public Comment getCommentAdminInfo(Long id, User user) {
        Comment comment;
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            // 관리자 계정이기 때문에 게시글 아이디만 일치하면 수정,삭제 가능
            comment = commentRepository.findById(id).orElseThrow(
                    () -> new ApiException(ExceptionEnum.NOT_FOUND_COMMENT_ADMIN)
            );
        } else {
            // 사용자 계정이므로 게시글 아이디와 작성자 이름이 있는지 확인하고 있으면 수정,삭제 가능
            comment = commentRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new ApiException(ExceptionEnum.NOT_FOUND_COMMENT)
            );
        }
        return comment;
    }

    // 게시글이 존재하는지 확인
    public Post getPostIdCheck(Long id) { // 게시글 id
        return postRepository.findById(id).orElseThrow(
                () -> new ApiException(ExceptionEnum.NOT_FOUND_POST_ALL)
        );
    }

    // 댓글 저장
    @Transactional
    public CommentResponseDto createComment(Long id,
                                            CommentRequestDto commentRequestDto,
                                            User user) {

        // 게시글 데이터베이스에 id와 일치하는 게시글이 있는지 확인 없으면 Error
        Post post = getPostIdCheck(id);
        // 요청받은 Dto로 DB에 저장할 객체 만들기, 토큰에 있는 작성자 이름을 같이 넣음
        // 새로운 Comment 객체를 생성하여 저장하는 데 사용
        Comment comment = commentRepository.save(new Comment(post, commentRequestDto, user));
        return new CommentResponseDto(comment);
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long postid,
                                            Long commentid,
                                            CommentRequestDto commentRequestDto,
                                            User user) {

        // 게시글 데이터베이스에 id와 일치하는 게시글이 있는지 확인 없으면 Error
        Post post = getPostIdCheck(postid);
        Comment comment = getCommentAdminInfo(commentid, user);
        comment.update(commentRequestDto); // 이미 존재하는 Comment 객체를 수정하고 업데이트하는 데 사용한다.
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    @Transactional
    public MessageResponse deleteComment(Long postid,
                                         Long commentid,
                                         User user) {

        getPostIdCheck(postid);
        getCommentAdminInfo(commentid, user);
        commentRepository.deleteById(commentid);
        return new MessageResponse(StatusEnum.OK);
    }
}
