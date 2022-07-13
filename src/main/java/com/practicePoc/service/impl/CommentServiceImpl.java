package com.practicePoc.service.impl;

import com.practicePoc.entity.Comment;
import com.practicePoc.entity.Post;
import com.practicePoc.exception.UserException;
import com.practicePoc.payload.CommentDto;
import com.practicePoc.repository.CommentRepository;
import com.practicePoc.repository.PostRepository;
import com.practicePoc.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto dto, Long id) {

        Post post = this.postRepository.findById(id).orElseThrow(() ->
                new UserException("The data not present for this id " + id, HttpStatus.BAD_REQUEST));

        Comment comment = this.modelMapper.map(dto, Comment.class);

        comment.setPost(post);
        Comment save = this.commentRepository.save(comment);
        return this.modelMapper.map(save, CommentDto.class);
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = this.commentRepository.findById(id).orElseThrow(() ->
                new UserException("The data not present for this id " + id, HttpStatus.BAD_REQUEST));
        this.commentRepository.delete(comment);
    }
}
