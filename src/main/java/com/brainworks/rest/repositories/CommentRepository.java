package com.brainworks.rest.repositories;

import com.brainworks.rest.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
   Set<Comment> findByPostPostIdAndUserUserId(Integer postId, Integer userId);
}
