package com.brainworks.rest.service;

import com.brainworks.rest.payloads.response.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto,Integer postId,Integer userId);
    void deleteComment(Integer commentId);
    String deleteCommentByUserIdAndPostId(Integer userId, Integer postId, Integer commentId);
}
