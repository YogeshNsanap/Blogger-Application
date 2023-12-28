package com.brainworks.rest.payloads.response;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Integer commentId;
    //private String comment;
    private String content;
   // private PostDto post;
    //private UserDto user;
}
