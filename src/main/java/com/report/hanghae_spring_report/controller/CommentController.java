package com.report.hanghae_spring_report.controller;

import com.report.hanghae_spring_report.dto.CommentRequestDto;
import com.report.hanghae_spring_report.dto.CommentResponseDto;
import com.report.hanghae_spring_report.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/post/{id}") // 댓글 작성
    public CommentResponseDto createComment(
            @PathVariable Long id, // 게시글 id
            @RequestBody CommentRequestDto commentRequestDto, // 댓글 내용
            HttpServletRequest request) { // 토큰
        return commentService.createComment(id, commentRequestDto, request);
    }

    @PutMapping("/post/{postid}/comment/{commentid}") // 댓글 수정
    public CommentResponseDto updateComment(
            @PathVariable Long postid, // 댓글을 달려고하는 게시판 id
            @PathVariable Long commentid, // 댓글 id
            @RequestBody CommentRequestDto commentRequestDto, // 수정 내용
            HttpServletRequest request) { // 토큰
        return commentService.updateComment(postid, commentid, commentRequestDto, request);
    }

    @DeleteMapping("/post/{postid}/comment/{commentid}") // 댓글 삭제
    public ResponseEntity deleteComment(
            @PathVariable Long postid, // 댓글을 달려고하는 게시판 id
            @PathVariable Long commentid, // 댓글 id
            HttpServletRequest request) { // 토큰
        return ResponseEntity.ok().body(commentService.deleteComment(postid, commentid, request));
    }
}
