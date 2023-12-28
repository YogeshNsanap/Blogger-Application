package com.brainworks.rest.configuration;

import com.brainworks.rest.entities.Post;
import com.brainworks.rest.exceptions.ResourceNotFoundException;
import com.brainworks.rest.repositories.CatogoryRepository;
import com.brainworks.rest.repositories.CommentRepository;
import com.brainworks.rest.repositories.PostRepository;
import com.brainworks.rest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppConstants {
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CatogoryRepository catogoryRepository;


    public static final String PAGE_NO="0";
    public static final String PAGE_SIZE="5";
    public static final String SORT_BY="postId";
    public static final String SORT_DIR="asc";
    public static final Integer ROLE_USER=502;
    public static final Integer ROLE_ADMIN=501;

    public Post findPost(Integer postId){
        Post Post=this.postRepository.findById (postId)
                .orElseThrow (()->new ResourceNotFoundException ("Post","post id",postId));
        return Post;
    }
}
