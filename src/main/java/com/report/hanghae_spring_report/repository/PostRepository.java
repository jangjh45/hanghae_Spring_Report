package com.report.hanghae_spring_report.repository;

import com.report.hanghae_spring_report.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();

    Optional<Post> findByIdAndUserId(Long id, Long user_id);
}
