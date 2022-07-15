package com.practicePoc.service.impl;

import com.practicePoc.entity.Category;
import com.practicePoc.entity.Post;
import com.practicePoc.entity.User;
import com.practicePoc.exception.UserException;
import com.practicePoc.payload.PostDto;
import com.practicePoc.payload.PostResponse;
import com.practicePoc.repository.CategoryRepository;
import com.practicePoc.repository.PostRepository;
import com.practicePoc.repository.UserRepository;
import com.practicePoc.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public PostDto createPost(PostDto postDto, Long userId, Long categoryId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException("the user not found in given id " + userId, HttpStatus.BAD_REQUEST));

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new UserException("the user not found in given id " + categoryId, HttpStatus.BAD_REQUEST));
        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedPost(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post save = this.postRepository.save(post);

        return this.modelMapper.map(save, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post = this.postRepository.findById(id).orElseThrow(() ->
                new UserException("The data not present for this id " + id, HttpStatus.BAD_REQUEST));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        Post save = this.postRepository.save(post);
        return this.modelMapper.map(save, PostDto.class);

    }

    @Override
    public void deletePost(long id) {
        Post post = this.postRepository.findById(id).orElseThrow(() ->
                new UserException("The data not present for this id " + id, HttpStatus.BAD_REQUEST));
        this.postRepository.deleteById(id);

    }

    @Override
    public List<PostDto> getAllPost() {
        List<Post> all = postRepository.findAll();
        List<PostDto> collect = all.stream().map((post) ->
                this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public PostDto getPostById(Long id) {

        Post post = this.postRepository.findById(id).orElseThrow(() ->
                new UserException("No data found from this id " + id, HttpStatus.BAD_REQUEST));

        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getPostsByCategory(Long id) {
        Category post = this.categoryRepository.findById(id).orElseThrow(() ->
                new UserException("The data not found for this given id " + id, HttpStatus.BAD_REQUEST));
        List<Post> byCategory = this.postRepository.findByCategory(post);
        List<PostDto> collect = byCategory.stream().map((post1 -> this.modelMapper.map(post1, PostDto.class))).collect(Collectors.toList());

        return collect;
    }

    @Override
    public List<PostDto> getPostsByUser(Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() ->
                new UserException("The data not found for this given id " + userId, HttpStatus.BAD_REQUEST));
        List<Post> allByUser = this.postRepository.findAllByUser(user);
        List<PostDto> collect = allByUser.stream().map((post1 -> this.modelMapper.map(post1, PostDto.class))).collect(Collectors.toList());

        return collect;
    }

    @Override
    public List<PostDto> searchPosts(String key) {
        List<Post> byTitleContaining = this.postRepository.searchByTitle("%" + key + "%");
        if (byTitleContaining.isEmpty()) {
            throw new UserException("No data found for this given name " + key, HttpStatus.BAD_REQUEST);
        }
        List<PostDto> collect = byTitleContaining.stream().map((post) ->
                this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return collect;
    }

    @Override
    public PostResponse getAllPostPagination(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort = null;
        if (sortDirection.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();

        } else {
            sort = Sort.by(sortBy).descending();
        }
        Pageable pageable = PageRequest.of(pageNumber, pageNumber, sort);
        Page<Post> all = postRepository.findAll(pageable);
        List<Post> content = all.getContent();
        List<PostDto> collect = content.stream().map((post1 -> this.modelMapper.map(post1, PostDto.class)))
                .collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(collect);
        postResponse.setPageNumber(all.getNumber());
        postResponse.setPageSize(all.getSize());
        postResponse.setTotalElement(all.getTotalElements());
        postResponse.setTotalPages(all.getTotalPages());
        postResponse.setLastPage(all.isLast());

        return postResponse;
    }

    @Override
    public List<PostDto> findByTitleContent(String title) {
        List<Post> byTitleContaining = this.postRepository.findByTitleContent("%" + title + "%");
        if (byTitleContaining.isEmpty()) {
            throw new UserException("No data found for this given name " + title, HttpStatus.BAD_REQUEST);
        }
        List<PostDto> collect = byTitleContaining.stream().map((post) ->
                this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return collect;
    }


}
