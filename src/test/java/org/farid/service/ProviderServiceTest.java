package org.farid.service;

import org.farid.configuration.resources.ApplicationProperties;
import org.farid.model.dto.BaseDTO;
import org.farid.model.mapper.ProviderMapper;
import org.farid.repository.ProviderRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class ProviderServiceTest {
    private final ApplicationProperties applicationProperties = mock(ApplicationProperties.class);

    private final ProviderRepository providerRepository = mock(ProviderRepository.class);
    private final ProviderMapper providerMapper = mock(ProviderMapper.class);
    private final ProviderService providerService = new ProviderService(applicationProperties,providerRepository , providerMapper);

    @Test
    void getAll() {
        BaseDTO result = providerService.providerList();
        assertNotNull(result.getData());
    }
}
