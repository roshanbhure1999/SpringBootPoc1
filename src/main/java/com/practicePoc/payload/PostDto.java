package com.practicePoc.payload;


import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class PostDto {

    private  Integer postId;

    private String title;

    private String content;

    private Date addedPost;

    private String imageName;

    private CategoryDto category;

    private UserDto  user;

    private Set<CommentDto> comments=new HashSet<>();


}
