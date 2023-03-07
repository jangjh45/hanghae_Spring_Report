package com.report.hanghae_spring_report.service;

import com.report.hanghae_spring_report.dto.CommentRequestDto;
import com.report.hanghae_spring_report.dto.CommentResponseDto;
import com.report.hanghae_spring_report.entity.Comment;
import com.report.hanghae_spring_report.entity.Post;
import com.report.hanghae_spring_report.entity.User;
import com.report.hanghae_spring_report.entity.UserEnum;
import com.report.hanghae_spring_report.jwt.JwtUtil;
import com.report.hanghae_spring_report.repository.CommentRepository;
import com.report.hanghae_spring_report.repository.PostRepository;
import com.report.hanghae_spring_report.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;

    public Post getPostIdCheck(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글에 연관된 게시글이 없습니다.")
        );
    }

    @Transactional
    public CommentResponseDto createComment(Long id,
                                            CommentRequestDto commentRequestDto,
                                            HttpServletRequest request) {

        // 게시글 데이터베이스에 id와 일치하는 게시글이 있는지 확인 없으면 Error
        Post post = getPostIdCheck(id);

        // 토큰 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) { // JWT의 유효성을 검증하여 올바른 JWT인지 확인
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 요청받은 Dto로 DB에 저장할 객체 만들기, 토큰에 있는 작성자 이름을 같이 넣음
            Comment comment = commentRepository.save(new Comment(post, commentRequestDto, user));
            return new CommentResponseDto(comment);
        }
        throw new IllegalArgumentException("로그인 안함(토큰 없음)");
    }

    @Transactional
    public CommentResponseDto updateComment(Long postid,
                                            Long commentid,
                                            CommentRequestDto commentRequestDto,
                                            HttpServletRequest request) {

        Post post = getPostIdCheck(postid);

        // 토큰 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) { // JWT의 유효성을 검증하여 올바른 JWT인지 확인
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Comment comment;
            // 관리자 계정이면 모든 댓글 수정가능
            if (user.getRole().equals(UserEnum.ADMIN)) {
                // 관리자 계정이기 때문에 댓글 id만 일치하면 수정 가능
                comment = commentRepository.findById(commentid).orElseThrow(
                        () -> new NullPointerException("(관리자)해당 댓글이 없습니다.")
                );
            } else {
                // 사용자 계정이므로 게시글 아이디와 작성자 id가 동일하면 수정 가능
                // 입력 받은 게시글 id와 토큰에서 가져온 userId와 일치하는 DB 조회
                comment = commentRepository.findByIdAndUserId(commentid, user.getId()).orElseThrow(
                        () -> new NullPointerException("(사용자)해당 댓글이 없습니다.")
                );
            }

            comment.update(commentRequestDto);
            return new CommentResponseDto(comment);
        }
        throw new IllegalArgumentException("로그인 안함(토큰 없음)");
    }
}
