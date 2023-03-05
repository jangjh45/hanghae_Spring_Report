package com.report.hanghae_spring_report.repository;

import com.report.hanghae_spring_report.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByIdDesc();

    Optional<Post> findByIdAndUsername(Long id, String username);
}
