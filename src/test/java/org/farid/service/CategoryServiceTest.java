package org.farid.service;

import org.farid.configuration.resources.ApplicationProperties;
import org.farid.model.dto.BaseDTO;
import org.farid.model.mapper.CategoryMapper;
import org.farid.repository.CategoryRepository;
import org.farid.repository.ProductRepository;
import org.farid.repository.ProviderRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class CategoryServiceTest {
    private final ApplicationProperties applicationProperties = mock(ApplicationProperties.class);
    private final CategoryRepository categoryRepository = mock(CategoryRepository.class);
    private final CategoryMapper categoryMapper = mock(CategoryMapper.class);


    private final CategoryService categoryService = new CategoryService(applicationProperties,categoryRepository,categoryMapper);

    @Test
    void getAll() {
        BaseDTO result = categoryService.getAllCategories();
        assertNotNull(result.getData());
    }
}
