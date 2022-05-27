package org.farid.service;

import org.farid.configuration.resources.ApplicationProperties;
import org.farid.service.intrface.ICategory;
import org.farid.model.domain.CategoryNewRequest;
import org.farid.model.dto.BaseDTO;
import org.farid.model.dto.CategoryDTO;
import org.farid.model.dto.MetaDTO;
import org.farid.model.entity.Category;
import org.farid.model.mapper.CategoryMapper;
import org.farid.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CategoryService implements ICategory {

    final ApplicationProperties applicationProperties;
    final CategoryRepository categoryRepository;
    final CategoryMapper categoryMapper;

    protected CategoryService(ApplicationProperties applicationProperties,
                              CategoryRepository categoryRepository,
                              CategoryMapper categoryMapper
    ) {
        this.applicationProperties = applicationProperties;
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }


    @Override
    public BaseDTO newCategory(CategoryNewRequest categoryNewRequest) {

        Category category = new Category();
        category.setTitle(categoryNewRequest.getTitle());
        category.setDescription(categoryNewRequest.getDescription());

        return BaseDTO.builder()
                .meta(MetaDTO.getInstance(applicationProperties))
                .data(categoryRepository.save(category))
                .build();
    }

    @Override
    public BaseDTO getAllCategories() {
          List<CategoryDTO>  categoryList  = new ArrayList<>();
        categoryRepository.findAll().forEach(i -> categoryList.add(categoryMapper.category(i)));

        return BaseDTO.builder()
                .meta(MetaDTO.getInstance(applicationProperties))
                .data(categoryList)
                .build();
    }
}
