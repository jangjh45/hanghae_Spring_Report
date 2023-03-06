package com.report.hanghae_spring_report.controller;

import com.report.hanghae_spring_report.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/comments")
public class CommentController {

    private final CommentService commentService;

//    @PostMapping("/create")
//    public
}
