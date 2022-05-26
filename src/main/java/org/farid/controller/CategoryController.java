package org.farid.controller;


import org.farid.model.domain.CategoryNewRequest;
import org.farid.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/categories")
public class CategoryController {

    final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "")
    public ResponseEntity<?>  getAllCategories(){
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<?>  newCategory(@Valid @RequestBody CategoryNewRequest categoryNewRequest){
        return new ResponseEntity<>(categoryService.newCategory(categoryNewRequest), HttpStatus.CREATED);
    }
}
