package com.report.hanghae_spring_report.service;

import com.report.hanghae_spring_report.dto.PostRequestDto;
import com.report.hanghae_spring_report.dto.PostResponseDto;
import com.report.hanghae_spring_report.entity.Post;
import com.report.hanghae_spring_report.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor // 생성자 만들어줌 다음 강의에서 가르쳐준다고함...
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        Post post = new Post(postRequestDto);
        // 데이터 베이스에 post 값을 저장하고 반환
        return new PostResponseDto(postRepository.save(post));
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostList() {
        // PostResponseDto 객체만 들어올 수 있는 리스트 생성
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        // 데이터 베이스에서 찾은 모든값을 리스트로 저장
        List<Post> postList = postRepository.findAllByOrderByModifiedAtDesc();
        for (Post post : postList) { // 리스트에서 하나씩 꺼내서 postResponseDtoList 리스트에 저장
            postResponseDtoList.add(new PostResponseDto(post));
            log.info("post = {}",post);
        }
        return postResponseDtoList;
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        Post post = checkPost(id);
        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto) {
        Post post = checkPost(id);
        if (post.getPassword().equals(postRequestDto.getPassword())) {
            post.update(postRequestDto);
        }
        return new PostResponseDto(post);
    }

    @Transactional
    public String deletePost(Long id, PostRequestDto postRequestDto) {
        Post post = checkPost(id);
        if (post.getPassword().equals(postRequestDto.getPassword())){
            postRepository.deleteById(id);
            return "삭제성공";
        }
        return "삭제실패";
    }

    private Post checkPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("일치하는 게시글 없음")
        );
    }
}
