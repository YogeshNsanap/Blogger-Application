package com.brainworks.rest.restControllers;


import com.brainworks.rest.configuration.AppConstants;
import com.brainworks.rest.payloads.response.ApiResponseStatus;
import com.brainworks.rest.payloads.response.PostDto;
import com.brainworks.rest.payloads.response.PostResponse;
import com.brainworks.rest.service.FileService;
import com.brainworks.rest.service.impl.PostServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class PostController {
    @Autowired
    PostServiceImpl postService;
    @Autowired
    FileService fileService;
    @Value ("${project.image}")
    private String path;

    @PostMapping("/{userId}/catogories/{catogoryId}")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer catogoryId
    ){
        PostDto createdPost = this.postService.createPost (postDto,userId,catogoryId);
        return new ResponseEntity<> (createdPost, HttpStatus.CREATED);
    }



    @DeleteMapping("/posts/{postId}")
    public ResponseEntity <ApiResponseStatus> deletePost(@PathVariable Integer postId){
        this.postService.deletePost (postId);
        return new ResponseEntity<> (new ApiResponseStatus ("Post Deleted successfully",true),HttpStatus.OK);
    }






    @PutMapping(value = "/posts/image/upload/{postId}",
            consumes = {"multipart/form-data","application/json","application/xml"})
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image")MultipartFile image,
                                                   @PathVariable Integer postId
    ) throws IOException {
        PostDto postDto=this.postService.getPostByPostId (postId);
        String fileName=this.fileService.uploadImage (path,image);
        postDto.setImageName (fileName);
        PostDto updatedPost=this.postService.updatePost (postDto,postId);
        return new ResponseEntity<> (updatedPost,HttpStatus.ACCEPTED);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId){
        PostDto updatedPost =this.postService.updatePost (postDto,postId);
        return new ResponseEntity<> (updatedPost,HttpStatus.ACCEPTED);
    }







    @GetMapping ("/posts")
    public ResponseEntity <PostResponse> getAllPost(
        @RequestParam (value = "pageNo",defaultValue = AppConstants.PAGE_NO,required = false)Integer pageNo,
        @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
        @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
        @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false)String sortDir
    ){
        PostResponse postResponse= this.postService.getAllPost (pageNo,pageSize,sortBy,sortDir);
        return new ResponseEntity<> (postResponse,HttpStatus.OK);
    }

    @GetMapping("/catogories/{catogoryId}/posts")
    public ResponseEntity <PostResponse> getPostByCatogory(
        @PathVariable Integer catogoryId,
        @RequestParam (value = "pageNo",defaultValue = AppConstants.PAGE_NO,required = false)Integer pageNo,
        @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
        @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
        @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false)String sortDir
    ){
        PostResponse postDtoList=this.postService.getPostByCatogory (catogoryId,pageNo,pageSize,sortBy,sortDir);
        return new ResponseEntity<> (postDtoList,HttpStatus.OK);
    }
    @GetMapping("/{userId}/posts")
    public ResponseEntity <PostResponse> getPostByUserId(
         @PathVariable Integer userId,
         @RequestParam (value = "pageNo",defaultValue = AppConstants.PAGE_NO,required = false)Integer pageNo,
         @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
         @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
         @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false)String sortDir
    ){
        PostResponse postDtoList = this.postService.getPostByUser (userId,pageNo,pageSize,sortBy,sortDir);
        return new ResponseEntity<> (postDtoList,HttpStatus.OK);
    }
    @GetMapping("/posts/{postId}")
    public ResponseEntity <PostDto> getPostByPostId(@PathVariable Integer postId){
        PostDto postdto=this.postService.getPostByPostId (postId);
        return new ResponseEntity<> (postdto,HttpStatus.FOUND);
    }
    @GetMapping("/{keyword}/search-post")
    public ResponseEntity<List<PostDto>> searchPost(@PathVariable ("keyword")String keyword){
       List<PostDto> postDtos= this.postService.searchPost (keyword);
       return new ResponseEntity<> (postDtos,HttpStatus.OK);
    }
    @GetMapping(value = "posts/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable String imageName,
                              HttpServletResponse response
    )throws IOException{
        InputStream resource=this.fileService.getResource (path,imageName);
        response.setContentType (MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy (resource,response.getOutputStream ());
    }
}