package com.brainworks.rest.repositories;

import com.brainworks.rest.entities.Catogory;
import com.brainworks.rest.entities.Comment;
import com.brainworks.rest.entities.Post;
import com.brainworks.rest.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post,Integer> {
    List<Post> findByUser (User user);
    List<Post> findByCatogory(Catogory catogory);

    Post findByComment(Comment comment);
    List<Post> findByTitleContaining(String keyword);
    Page <Post> findByCatogoryCid(Integer catogoryId, Pageable pageable);
    Page<Post> findByUserUserId(Integer userId, Pageable pageable);

}
