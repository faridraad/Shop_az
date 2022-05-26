package org.farid.service;

import org.farid.configuration.resources.ApplicationProperties;
import org.farid.intrface.IProductProvider;
import org.farid.model.domain.ProductProviderNewRequest;
import org.farid.model.dto.BaseDTO;
import org.farid.model.dto.MetaDTO;
import org.farid.model.dto.ProductProviderDTO;
import org.farid.model.entity.Product;
import org.farid.model.entity.ProductProvider;
import org.farid.model.entity.Provider;
import org.farid.model.mapper.ProductProviderMapper;
import org.farid.repository.ProductProviderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class ProductProviderService implements IProductProvider {

    final ApplicationProperties applicationProperties;
    final ProductProviderRepository  productProviderRepository;
    final ProductProviderMapper productProviderMapper;

//    @Autowired
//    ;

    public ProductProviderService(ApplicationProperties applicationProperties,
                                  ProductProviderRepository productProviderRepository,
                                  ProductProviderMapper productProviderMapper) {
        this.applicationProperties = applicationProperties;
        this.productProviderRepository = productProviderRepository;
        this.productProviderMapper = productProviderMapper;
    }

    @Override
    public BaseDTO newProductProvider(ProductProviderNewRequest productProviderNewRequest) {
        Product product =new Product();
        product.setId(productProviderNewRequest.getProductId());

        Provider provider  =new Provider();
        provider.setId(productProviderNewRequest.getProviderId());

        ProductProvider productProvider = new ProductProvider();
        productProvider.setProduct(product);
        productProvider.setProvider(provider);

        return BaseDTO.builder()
                .meta(MetaDTO.getInstance(applicationProperties))
                .data(productProviderRepository.save(productProvider))
                .build();
    }


    @Override
    public BaseDTO getProviders(String providerId) {
        List<Long> productIdList = Stream.of(providerId.split(","))
                .map(String::trim)
                .map(Long::parseLong)
                .collect(Collectors.toList());

        List<ProductProviderDTO> productProviderDTOS  = new ArrayList<>();
        productProviderRepository.findByProductIdIn(productIdList).forEach(i -> productProviderDTOS.add(productProviderMapper.productProvider(i)));
        return BaseDTO.builder()
                .meta(MetaDTO.getInstance(applicationProperties))
                .data(productProviderDTOS)
                .build();
    }

}
