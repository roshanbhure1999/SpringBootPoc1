package com.practicePoc.controller;


import com.practicePoc.exception.UserException;
import com.practicePoc.payload.PostDto;
import com.practicePoc.payload.PostResponse;
import com.practicePoc.service.FileService;
import com.practicePoc.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPosts(@RequestBody PostDto postDto,
                                               @PathVariable Long userId,
                                               @PathVariable Long categoryId) {
        PostDto post = this.postService.createPost(postDto, userId, categoryId);

        return new ResponseEntity<PostDto>(post, HttpStatus.CREATED);

    }


    @GetMapping("/user/{id}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Long id) {
        List<PostDto> postsByUser = postService.getPostsByUser(id);
        return new ResponseEntity<List<PostDto>>(postsByUser, HttpStatus.OK);
    }

    @GetMapping("/category/{id}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Long id) {
        List<PostDto> postsByCategory = postService.getPostsByCategory(id);
        return new ResponseEntity<List<PostDto>>(postsByCategory, HttpStatus.OK);
    }

    @GetMapping("/get-all-posts")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> postsByCategory = postService.getAllPost();
        return new ResponseEntity<List<PostDto>>(postsByCategory, HttpStatus.OK);
    }

    @GetMapping("/get-all-posts/{id}")
    public ResponseEntity<PostDto> getById(@PathVariable Long id) {
        PostDto postById = postService.getPostById(id);
        return new ResponseEntity<PostDto>(postById, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{id}")
    public UserException deletePost(@PathVariable Long id) {
        this.postService.deletePost(id);
        return new UserException("The Posts delete successfully for given id" + id, HttpStatus.OK);
    }

    @PostMapping("/update-post/{id}")
    public ResponseEntity<PostDto> updatePosts(@RequestBody PostDto postDto,
                                               @PathVariable Long postId) {
        PostDto post = this.postService.updatePost(postDto, postId);

        return new ResponseEntity<PostDto>(post, HttpStatus.OK);

    }

    @GetMapping("/get-all-pagination")
    public ResponseEntity<PostResponse> getByPagination(@RequestParam(value = "pageNumber",
            defaultValue = "0", required = false) Integer pageNumber,
                                                        @RequestParam(value = "pageSize",
                                                                defaultValue = "10", required = false) Integer pageSize,
                                                        @RequestParam(value = "sortBy", defaultValue = "PostId", required = false) String sortBy, @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDirection) {
        PostResponse allPostPagination = postService.getAllPostPagination(pageNumber, pageSize, sortBy, sortDirection);
        return new ResponseEntity<PostResponse>(allPostPagination, HttpStatus.OK);
    }

    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchByTitle(@PathVariable String keyword) {
        List<PostDto> postDtos = this.postService.searchPosts(keyword);
        return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
    }

    // Post image
        @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
                                                   @PathVariable Long postId) throws IOException {
        PostDto postById = this.postService.getPostById(postId);
        String fileName = this.fileService.uploadImage(path, image);
        postById.setImageName(fileName);
        PostDto postDto = this.postService.updatePost(postById, postId);
        return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);

    }

    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName,
                              HttpServletResponse httpServletResponse) throws IOException {
        InputStream resource = this.fileService.getResource(path, imageName);
        httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, httpServletResponse.getOutputStream());

    }


}
