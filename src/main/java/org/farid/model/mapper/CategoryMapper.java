package org.farid.model.mapper;

import org.farid.model.dto.CategoryDTO;
import org.farid.model.entity.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {
    CategoryDTO category(Category category);
}
