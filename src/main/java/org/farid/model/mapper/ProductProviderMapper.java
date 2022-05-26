package org.farid.model.mapper;

import org.farid.model.dto.ProductProviderDTO;
import org.farid.model.entity.ProductProvider;
import org.mapstruct.Mapper;

@Mapper
public interface ProductProviderMapper {
    ProductProviderDTO productProvider(ProductProvider productProvider);
}
