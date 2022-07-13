package com.practicePoc.service;

import com.practicePoc.payload.CategoryDto;
import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto categoryUpdate(CategoryDto categoryDto, Long id);

    CategoryDto getCategoryById(Long id);

    List<CategoryDto> getAllCategory();

    void deleteCategoryById(Long id);

}
