package com.brainworks.rest.service;

import com.brainworks.rest.payloads.response.PostDto;
import com.brainworks.rest.payloads.response.PostResponse;

import java.util.List;

public interface PostServices {

    //create
    PostDto createPost (PostDto postDto,Integer userId,Integer CatogoryId);

    //getAllPost
    PostResponse getAllPost(Integer pageNo, Integer pageSize,String sortBy,String sortDir);

    //getAllpostBycatogory
    PostResponse getPostByCatogory(Integer catogoryid,Integer pageNo, Integer pageSize,String sortBy,String sortDir);

    //getAlllpostByuser
    PostResponse getPostByUser(Integer userId,Integer pageNo, Integer pageSize,String sortBy,String sortDir);

    //delete
    void deletePost(Integer postId);

    //update
    PostDto updatePost(PostDto postDto,Integer postId);

    //get
    PostDto getPostByPostId(Integer postId);

    List <PostDto> searchPost(String keyword);

    //getAll
}
