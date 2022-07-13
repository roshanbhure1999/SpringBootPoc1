package com.practicePoc.service;

import com.practicePoc.payload.PostDto;
import com.practicePoc.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto,Long userId,Long categoryId);

    PostDto updatePost(PostDto postDto, Long id);

    void deletePost(long id);

    List<PostDto> getAllPost();

    PostDto getPostById(Long id);

    List<PostDto> getPostsByCategory(Long CatId);

    List<PostDto> getPostsByUser(Long userId);

    List<PostDto> searchPosts(String keyword);

    PostResponse getAllPostPagination(int pageNumber, int pageSize, String sortBy, String sortDirection);
}
