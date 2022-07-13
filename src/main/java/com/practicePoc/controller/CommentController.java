package com.practicePoc.controller;

import com.practicePoc.exception.ApiResponse;
import com.practicePoc.payload.CategoryDto;
import com.practicePoc.payload.CommentDto;
import com.practicePoc.payload.UserDto;
import com.practicePoc.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/create-comment/{postId}")
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto dto,@PathVariable Long postId) {
        CommentDto comment = commentService.createComment(dto, postId);
        return new ResponseEntity<CommentDto>(comment, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Long commentId) {
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("The Comment delete Successfully",true),
                HttpStatus.OK);
    }


}
