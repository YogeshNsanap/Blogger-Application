package com.brainworks.rest.payloads.response;

import com.brainworks.rest.entities.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private  Integer postId;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    private String imageName;
    private Date addedDate;
    private CatogoryDto catogory;
    private UserDto user;
    private Set<CommentDto> comment;

}
