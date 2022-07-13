package com.practicePoc.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "Category")
public class Category {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long categoryId;

        @Column(name = "CategoryTitle",length = 100,nullable = false)
        private String categoryTitle;

        @Column(name = "categoryDescription",length = 200,nullable = false)
        private String categoryDescription;


        @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
        private List<Post> posts=new ArrayList<>();


}
