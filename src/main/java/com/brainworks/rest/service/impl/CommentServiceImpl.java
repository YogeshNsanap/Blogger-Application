package com.brainworks.rest.service.impl;

import com.brainworks.rest.configuration.AppConstants;
import com.brainworks.rest.entities.Comment;
import com.brainworks.rest.entities.Post;
import com.brainworks.rest.entities.User;
import com.brainworks.rest.exceptions.ResourceNotFoundException;
import com.brainworks.rest.payloads.response.CommentDto;
import com.brainworks.rest.repositories.CommentRepository;
import com.brainworks.rest.repositories.PostRepository;
import com.brainworks.rest.repositories.UserRepository;
import com.brainworks.rest.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AppConstants appConstants;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {

        Post post = appConstants.findPost (postId);
        User user=this.userRepository.findById (userId)
                .orElseThrow (()->new ResourceNotFoundException ("User","userId",userId));
        Comment comment = setValuesForComment (commentDto, post, user);
        Comment savedComment=this.commentRepository.save (comment);
        return this.modelMapper.map (savedComment,CommentDto.class);
    }
    private Comment setValuesForComment(CommentDto commentDto, Post post, User user) {
        Comment comment=this.modelMapper.map (commentDto, Comment.class);
        comment.setCommentId (commentDto.getCommentId ());
        comment.setContent (commentDto.getContent ());
        comment.setPost (post);
        comment.setUser (user);
        return comment;
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment=this.commentRepository.findById (commentId)
                .orElseThrow (()->new ResourceNotFoundException ("Comment","commentId",commentId));
        this.commentRepository.delete (comment);
    }

    @Override
    public String deleteCommentByUserIdAndPostId(Integer userId, Integer postId,Integer commentId) {

        Comment comment = this.commentRepository.findById(commentId)
                .orElseThrow(()->new ResourceNotFoundException("Comment", "commentId", commentId));
        Set<Comment> comments = this.commentRepository.findByPostPostIdAndUserUserId (postId, userId);
        comments.stream ().forEach (comment1 -> System.out.println (comment1.getCommentId ()));
        System.out.println ("No of comments : "+comments.size ());
        if(comments.contains (comment)){
            this.commentRepository.delete (comment);
        }else {
            throw new ResourceNotFoundException ("Comment","commentId",commentId);
        }
        return "Comment deleted";
    }
}