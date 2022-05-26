package org.farid.service;

import org.farid.configuration.exception.ServiceException;
import org.farid.model.domain.ProviderNewRequest;
import org.farid.model.dto.BaseDTO;
import org.farid.model.dto.MetaDTO;
import org.farid.model.dto.ProviderDTO;
import org.farid.model.entity.Product;
import org.farid.model.entity.Provider;
import org.farid.model.mapper.ProviderMapper;
import org.farid.repository.ProviderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.farid.configuration.resources.ApplicationProperties;
import org.farid.intrface.IProvider;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProviderService implements IProvider {

    final ApplicationProperties applicationProperties;
    final ProviderRepository providerRepository;
    final ProviderMapper providerMapper;
    protected ProviderService(
            ApplicationProperties applicationProperties,
            ProviderRepository providerRepository,
            ProviderMapper providerMapper) {
        this.applicationProperties = applicationProperties;
        this.providerRepository = providerRepository;
        this.providerMapper = providerMapper;
    }

    @Override
    public BaseDTO providerList() {
        List<ProviderDTO> providerList = new ArrayList<>();
        this.providerRepository.findAll().forEach(i-> providerList.add(providerMapper.provider(i)));
        return BaseDTO.builder()
                .meta(MetaDTO.getInstance(applicationProperties))
                .data(providerList)
                .build();
    }

    @Override
    public BaseDTO newProvider(ProviderNewRequest providerNewRequest) {
        Provider provider = new Provider();
        provider.setTitle(providerNewRequest.getTitle());
        provider.setAddress(providerNewRequest.getAddress());
        provider.setPhoneNumber(providerNewRequest.getPhoneNumber());
        return BaseDTO.builder()
                .meta(MetaDTO.getInstance(applicationProperties))
                .data(providerRepository.save(provider))
                .build();
    }

    @Override
    public BaseDTO getProviderById(Integer id) {
        Provider provider = this.providerById(id);
        ProviderDTO providerDTO = providerMapper.provider(provider);
        return BaseDTO.builder()
                .meta(MetaDTO.getInstance(applicationProperties))
                .data(providerDTO)
                .build();
    }

    Provider providerById(Integer id){
        Provider provider = providerRepository.findById(id).orElseThrow(
                () -> ServiceException.builder()
                        .code(applicationProperties.getCode("application.message.notFoundRecord.code"))
                        .message(applicationProperties.getProperty("application.message.notFoundRecord.text"))
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .build()

        );
        return provider;
    }
}
