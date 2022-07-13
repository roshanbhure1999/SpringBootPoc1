package com.practicePoc.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "coments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long CommentId;

    private String content;

    @ManyToOne
    private Post post;
}
