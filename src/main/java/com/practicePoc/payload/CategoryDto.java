package com.practicePoc.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CategoryDto {

    private Long categoryId;
    @NotEmpty()
    @Size(min = 4,message = "about.categoryTitle the size must me 4 character ")
    private String categoryTitle;
    @NotEmpty()
    @Size(min = 4,message = "about.categoryDescription the size must me 4 character  ")
    private String categoryDescription;
}
