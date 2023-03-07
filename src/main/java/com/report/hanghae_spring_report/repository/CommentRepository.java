package com.report.hanghae_spring_report.repository;

import com.report.hanghae_spring_report.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndUserId(Long commentid, Long userid);
}
