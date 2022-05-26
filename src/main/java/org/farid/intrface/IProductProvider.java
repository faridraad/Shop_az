package org.farid.intrface;

import org.farid.model.domain.ProductProviderNewRequest;
import org.farid.model.dto.BaseDTO;

public interface IProductProvider {
    BaseDTO newProductProvider(ProductProviderNewRequest productProviderNewRequest);
    BaseDTO getProviders(String providerId);
}
