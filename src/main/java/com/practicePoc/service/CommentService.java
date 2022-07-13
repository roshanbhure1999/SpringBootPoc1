package com.practicePoc.service;

import com.practicePoc.payload.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto  dto,Long id);

    void deleteComment(Long id);
}
