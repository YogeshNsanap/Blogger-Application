package com.brainworks.rest.restControllers;

import com.brainworks.rest.payloads.response.ApiResponseStatus;
import com.brainworks.rest.payloads.response.CommentDto;
import com.brainworks.rest.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/comments")
public class CommentController {
    @Autowired
    CommentService commentService;
    @PostMapping("posts/{postId}/users/{userId}")
    public ResponseEntity<CommentDto> createComment(
            @RequestBody CommentDto commentDto,
            @PathVariable Integer postId,
            @PathVariable Integer userId
    ){
        CommentDto createdDto=this.commentService.createComment (commentDto,postId,userId );
        return new ResponseEntity<> (createdDto, HttpStatus.CREATED);
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponseStatus> deleteComment(
            @PathVariable Integer commentId
    ){
        this.commentService.deleteComment (commentId);
        return  new ResponseEntity<> (new ApiResponseStatus ("Comment deleted successfully",true),HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}/post/{postId}/comment/{commentId}")
    public ResponseEntity<String> deleteCommentsByUserAndPostId(Integer userId, Integer postId, Integer commentId){

        String delete = this.commentService.deleteCommentByUserIdAndPostId(userId, postId, commentId);

        return new ResponseEntity<String>(delete, HttpStatus.OK);
    }
}
