package com.practicePoc.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
@Data
public class Post{
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long postId;

        @Column(name = "post_title",length = 100,nullable = false)
        private String title;

        @Column(name = "post_content",length = 100,nullable = false)
        private String content;

        @Column(name = "post_imageName",length = 100,nullable = false)
        private String imageName;

        @Column(name = "post_addedPost")
        private Date addedPost;

        @ManyToOne
        @JoinColumn(name = "category_category_id")
        private Category category;

        @ManyToOne()
        @JoinColumn(name = "user_id")
        private User user;

        @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
        private Set<Comment> commentSet=new HashSet<>();




}
