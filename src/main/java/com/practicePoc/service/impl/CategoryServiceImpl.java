package com.practicePoc.service.impl;

import com.practicePoc.entity.Category;
import com.practicePoc.entity.User;
import com.practicePoc.exception.UserException;
import com.practicePoc.payload.CategoryDto;
import com.practicePoc.payload.UserDto;
import com.practicePoc.repository.CategoryRepository;
import com.practicePoc.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category map = this.modelMapper.map(categoryDto, Category.class);
        Category save = this.repository.save(map);
        return this.modelMapper.map(save,CategoryDto.class);
    }

    @Override
    public CategoryDto categoryUpdate(CategoryDto categoryDto, Long id) {
        Category byId = this.repository.findById(id).
                orElseThrow(()->new UserException("user Not found this given id "+id, HttpStatus.BAD_REQUEST));
         byId.setCategoryTitle(categoryDto.getCategoryTitle());
         byId.setCategoryDescription(categoryDto.getCategoryDescription());
        Category save = this.repository.save(byId);
        return this.modelMapper.map(save,CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = this.repository.findById(id).orElseThrow(
                () -> new UserException("user Not found this given id " + id, HttpStatus.BAD_REQUEST));
        return this.modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> all = this.repository.findAll();
        List<CategoryDto> collect = all.stream().map((category ->
                this.modelMapper.map(all, CategoryDto.class))).collect(Collectors.toList());
        return collect;
    }

    @Override
    public void deleteCategoryById(Long id) {
        Category category = this.repository.findById(id).orElseThrow(
                () -> new UserException("user Not found this given id " + id, HttpStatus.BAD_REQUEST));
        this.repository.delete(category);


    }


}
