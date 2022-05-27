package org.farid.service.intrface;


import org.farid.model.domain.CategoryNewRequest;
import org.farid.model.dto.BaseDTO;

public interface ICategory {
    BaseDTO newCategory(CategoryNewRequest categoryNewRequest);
    BaseDTO getAllCategories();
}
