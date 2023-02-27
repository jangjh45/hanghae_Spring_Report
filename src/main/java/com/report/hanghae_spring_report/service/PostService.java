package com.report.hanghae_spring_report.service;

import com.report.hanghae_spring_report.dto.PostDto;
import com.report.hanghae_spring_report.entity.Post;
import com.report.hanghae_spring_report.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor // 생성자 만들어줌 다음 강의에서 가르쳐준다고함...
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Post createPost(PostDto postDto) {
        Post post = new Post(postDto);
        postRepository.save(post);
        return post;
    }

    @Transactional(readOnly = true)
    public List<Post> getPostList() {
        return postRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional(readOnly = true)
    public Post getPost(Long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        return postOptional.orElseThrow(() -> new NoSuchElementException("Post not found"));
    }

    @Transactional
    public PostDto updatePost(Long id, PostDto postDto) {
        System.out.println(postDto);
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다."));
        if (post.getPassword().equals(postDto.getPassword())) {
            System.out.println("비밀번호가 일치합니다.");
            post.update(postDto);
        }
        return postDto;
    }

    @Transactional
    public String deletePost(Long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다."));
        if (post.getPassword().equals(postDto.getPassword())){
            postRepository.deleteById(id);
            return "삭제성공";
        }
        return "삭제실패";
    }
}
