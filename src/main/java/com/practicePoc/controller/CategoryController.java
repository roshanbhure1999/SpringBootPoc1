package com.practicePoc.controller;

import com.practicePoc.exception.UserException;
import com.practicePoc.payload.CategoryDto;
import com.practicePoc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create-category")
    public ResponseEntity<CategoryDto> createUser(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto category = categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(category, HttpStatus.CREATED);
    }

    @PutMapping("/update-category/{id}")
    public ResponseEntity<CategoryDto> updateUser(@Valid @RequestBody CategoryDto categoryDto,

                                                  @PathVariable Long id) {
        CategoryDto category = categoryService.categoryUpdate(categoryDto, id);
        return new ResponseEntity<CategoryDto>(category, HttpStatus.OK);
    }

    @GetMapping("/get_category_By/{id}")
    public ResponseEntity<CategoryDto> getById(@PathVariable Long id) {
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CategoryDto>> getAllUSer() {
        List<CategoryDto> allCategory = categoryService.getAllCategory();
        return ResponseEntity.ok(allCategory);
    }

    @DeleteMapping("/delete_category/{id}")
    public ResponseEntity<UserException> deleteUser(@PathVariable Long id) {
        this.categoryService.deleteCategoryById(id);
        return new ResponseEntity<UserException>(new UserException
                ("User is delete successfully", HttpStatus.OK), HttpStatus.OK);
    }
}
