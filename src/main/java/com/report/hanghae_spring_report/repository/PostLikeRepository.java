package com.report.hanghae_spring_report.repository;

import com.report.hanghae_spring_report.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByPostIdAndUserId(Long id, Long user_id);

    Optional<PostLike> deleteByPostIdAndUserId(Long id, Long User_id);
}
