package com.brainworks.rest.payloads.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private List <PostDto> content;
    private Long totalElements;
    private Integer pageNo;
    private Integer pageSize;
    private Integer totalPages;
    private Boolean lastPage;
}
