package com.report.hanghae_spring_report.repository;

import com.report.hanghae_spring_report.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByCommentIdAndUserId(Long id, Long user_id);

    Optional<CommentLike> deleteByCommentIdAndUserId(Long id, Long User_id);
}
