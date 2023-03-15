package com.report.hanghae_spring_report.controller;

import com.report.hanghae_spring_report.dto.CommentRequestDto;
import com.report.hanghae_spring_report.dto.CommentResponseDto;
import com.report.hanghae_spring_report.security.UserDetailsImpl;
import com.report.hanghae_spring_report.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/comments")
public class CommentController {

    private final CommentService commentService;

    /**
     * @AuthenticationPrincipal 컨트롤러 메서드의 파라미터로 현재 인증된 사용자의 정보를 주입
     */

    // @Secured(UserRoleEnum.Authority.ADMIN) // 이 메서드는 ADMIN 만 사용가능
    @PostMapping("/post/{id}") // 댓글 작성
    public CommentResponseDto createComment(
            @PathVariable Long id, // 게시글 id
            @RequestBody @Valid CommentRequestDto commentRequestDto, // 댓글 내용
            @AuthenticationPrincipal UserDetailsImpl userDetails) { // 인증된 사용자의 정보
        return commentService.createComment(id, commentRequestDto, userDetails.getUser());
    }

    @PutMapping("/post/{postid}/comment/{commentid}") // 댓글 수정
    public CommentResponseDto updateComment(
            @PathVariable Long postid, // 댓글을 달려고하는 게시판 id
            @PathVariable Long commentid, // 댓글 id
            @RequestBody @Valid CommentRequestDto commentRequestDto, // 수정 내용
            @AuthenticationPrincipal UserDetailsImpl userDetails) { // 인증된 사용자의 정보
        return commentService.updateComment(postid, commentid, commentRequestDto, userDetails.getUser());
    }

    @DeleteMapping("/post/{postid}/comment/{commentid}") // 댓글 삭제
    public ResponseEntity deleteComment(
            @PathVariable Long postid, // 댓글을 달려고하는 게시판 id
            @PathVariable Long commentid, // 댓글 id
            @AuthenticationPrincipal UserDetailsImpl userDetails) { // 인증된 사용자의 정보
        return ResponseEntity.ok().body(commentService.deleteComment(postid, commentid, userDetails.getUser()));
    }
}
