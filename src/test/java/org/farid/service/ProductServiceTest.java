package org.farid.service;

import org.farid.configuration.resources.ApplicationProperties;
import org.farid.model.dto.BaseDTO;
import org.farid.repository.ProductRepository;
import org.farid.repository.ProviderRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ProductServiceTest {
    private final ApplicationProperties applicationProperties = mock(ApplicationProperties.class);
    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final ProviderRepository providerRepository = mock(ProviderRepository.class);
    private final ProductService productService = new ProductService(applicationProperties,
            productRepository, providerRepository);

    @Test
    void review() {
        BaseDTO result = productService.review(1, 10, 39);
        assertNotNull(result.getData());
    }
}
