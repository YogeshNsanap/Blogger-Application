package com.brainworks.rest.service.impl;

import com.brainworks.rest.entities.Catogory;
import com.brainworks.rest.entities.Post;
import com.brainworks.rest.entities.User;
import com.brainworks.rest.exceptions.ResourceNotFoundException;
import com.brainworks.rest.payloads.response.PostDto;
import com.brainworks.rest.payloads.response.PostResponse;
import com.brainworks.rest.repositories.CatogoryRepository;
import com.brainworks.rest.repositories.PostRepository;
import com.brainworks.rest.repositories.UserRepository;
import com.brainworks.rest.service.PostServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostServices {
    @Autowired
    PostRepository postRepository;
    @Autowired
    CatogoryRepository catogoryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer catogoryId) {

        User user= this.userRepository.findById (userId)
                .orElseThrow (()->new ResourceNotFoundException ("User","UserId",userId));
        //System.out.println (user);

        Catogory catogory= this.catogoryRepository.findById (catogoryId)
                .orElseThrow (()->new ResourceNotFoundException ("Catogory","CatogoryId",catogoryId));
        //System.out.println (catogory);

        Post post=this.modelMapper.map(postDto,Post.class);
        post.setImageName ("Default.png");
        post.setAddedDate (new Date());
        post.setUser (user);
        post.setCatogory (catogory);
        Post newPost=this.postRepository.save (post);
        return this.modelMapper.map(newPost,PostDto.class);
    }

    @Override
    public PostResponse getAllPost(Integer pageNo, Integer pageSize,String sortBy,String sortDir) {

        Sort sort=sortDir.equalsIgnoreCase ("asc")?Sort.by (sortBy).ascending ():Sort.by (sortBy).descending ();
        Pageable pageable= PageRequest.of (pageNo,pageSize,sort);
        Page <Post> postPage=this.postRepository.findAll (pageable);
        List<Post> posts=postPage.getContent ();
        List<PostDto> collect = posts.stream ()
                .map ((post) -> this.modelMapper.map (post, PostDto.class))
                .collect (Collectors.toList ());

        PostResponse postResponse = getPostResponse (collect, postPage);
        return postResponse;
    }
    private static PostResponse getPostResponse(List<PostDto> collect, Page<Post> postPage) {
        PostResponse postResponse=new PostResponse ();
        postResponse.setContent (collect);
        postResponse.setPageNo (postPage.getNumber ());
        postResponse.setPageSize (postPage.getSize ());
        postResponse.setTotalPages (postPage.getTotalPages ());
        postResponse.setTotalElements (postPage.getTotalElements ());
        postResponse.setLastPage (postPage.isLast ());
        return postResponse;
    }

    @Override
    public PostResponse getPostByUser(Integer userId, Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        User user = this.userRepository.findById (userId)
                .orElseThrow (()->new ResourceNotFoundException ("User","userID",userId));
        Sort sort=sortDir.equalsIgnoreCase ("asc")?Sort.by (sortBy).ascending ():Sort.by (sortBy).descending ();
        Pageable pageable= PageRequest.of (pageNo,pageSize,sort);
        Page<Post> postPage=this.postRepository.findByUserUserId (userId,pageable);
        List<Post> posts=postPage.getContent ();
        List<PostDto> collect = posts.stream ()
                .map ((post) -> this.modelMapper.map (post, PostDto.class))
                .collect (Collectors.toList ());
        PostResponse postResponse=getPostResponseForUser (collect, postPage);
        return postResponse;
    }
    private static PostResponse getPostResponseForUser(List<PostDto> collect, Page<Post> postPage) {
        PostResponse postResponse=new PostResponse ();
        postResponse.setContent (collect);
        postResponse.setPageNo (postPage.getNumber ());
        postResponse.setPageSize (postPage.getSize ());
        postResponse.setTotalPages (postPage.getTotalPages ());
        postResponse.setTotalElements (postPage.getTotalElements ());
        postResponse.setLastPage (postPage.isLast ());
        return postResponse;
    }

    @Override
    public void deletePost(Integer postId){
        Post post=this.postRepository.findById (postId)
                .orElseThrow (()->new ResourceNotFoundException ("Post","post Id",postId));
        this.postRepository.delete (post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post=this.postRepository.findById (postId)
                .orElseThrow (()->new ResourceNotFoundException ("Post","postId",postId));
        post.setTitle (postDto.getTitle ());
        post.setContent (postDto.getContent ());
        post.setImageName (postDto.getImageName ());
        Post updatedPost =this.postRepository.save (post);
        return this.modelMapper.map (updatedPost,PostDto.class);
    }

    @Override
    public PostDto getPostByPostId(Integer postId) {
        Post post = this.postRepository.findById (postId)
                .orElseThrow (()->new ResourceNotFoundException ("Post","post id",postId));
        PostDto postDto =this.modelMapper.map (post,PostDto.class);
        return postDto;
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        List<Post> post=this.postRepository.findByTitleContaining (keyword);
        List<PostDto> postDtos=post.stream ()
                .map (post1 ->this.modelMapper.map (post1,PostDto.class))
                .collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public PostResponse getPostByCatogory(Integer catogoryId,Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        Catogory catogory=this.catogoryRepository.findById (catogoryId)
                .orElseThrow (()->new ResourceNotFoundException ("Catogory","catogoryId",catogoryId));

        Sort sort=sortDir.equalsIgnoreCase ("asc")?Sort.by (sortBy).ascending ():Sort.by (sortBy).descending ();
        Pageable pageable= PageRequest.of (pageNo,pageSize,sort);
        Page<Post> postPage=this.postRepository.findByCatogoryCid (catogoryId,pageable);
        List<Post> posts=postPage.getContent ();

        List<PostDto> collect = posts.stream ()
                .map ((post) -> this.modelMapper.map (post, PostDto.class))
                .collect (Collectors.toList ());
        PostResponse postResponse=getPostResponseForCatogory (collect, postPage);
        return postResponse;
    }
    private static PostResponse getPostResponseForCatogory(List<PostDto> collect, Page<Post> postPage) {
        PostResponse postResponse=new PostResponse ();
        postResponse.setContent (collect);
        postResponse.setPageNo (postPage.getNumber ());
        postResponse.setPageSize (postPage.getSize ());
        postResponse.setTotalPages (postPage.getTotalPages ());
        postResponse.setTotalElements (postPage.getTotalElements ());
        postResponse.setLastPage (postPage.isLast ());
        return postResponse;
    }
}
