package org.farid.model.mapper;

import org.farid.model.dto.ProviderDTO;
import org.farid.model.entity.Provider;
import org.mapstruct.Mapper;

@Mapper
public interface ProviderMapper {
    ProviderDTO provider(Provider category);
}
