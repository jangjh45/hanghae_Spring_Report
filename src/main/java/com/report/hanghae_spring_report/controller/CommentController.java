package com.report.hanghae_spring_report.controller;

import com.report.hanghae_spring_report.dto.CommentRequestDto;
import com.report.hanghae_spring_report.dto.CommentResponseDto;
import com.report.hanghae_spring_report.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/create/{id}")
    public CommentResponseDto createComment(
            @PathVariable Long id,
            @RequestBody CommentRequestDto commentRequestDto,
            HttpServletRequest request) {
        return commentService.createComment(id, commentRequestDto, request);
    }

    @PutMapping("/update/{postid}/{commentid}")
    public CommentResponseDto updateComment(@PathVariable Long postid,
                                            @PathVariable Long commentid,
                                            @RequestBody CommentRequestDto commentRequestDto,
                                            HttpServletRequest request) {
        return commentService.updateComment(postid, commentid, commentRequestDto, request);
    }
}
