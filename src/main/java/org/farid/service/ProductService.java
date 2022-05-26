package org.farid.service;

import org.farid.configuration.exception.ServiceException;
import org.farid.model.dto.*;
import org.farid.model.entity.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.farid.configuration.resources.ApplicationProperties;
import org.farid.intrface.IProduct;
import org.farid.model.domain.ProductChangeRequest;
import org.farid.model.domain.ProductNewRequest;
import org.farid.model.entity.Product;
import org.farid.repository.ProductRepository;
import org.farid.repository.ProviderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.farid.utility.CommonUtility.distinctByKey;

@Service
public class ProductService implements IProduct {

    final ApplicationProperties applicationProperties;
    final ProductRepository productRepository;
    final ProviderRepository providerRepository;

    protected ProductService(ApplicationProperties applicationProperties,
                             ProductRepository productRepository,
                             ProviderRepository providerRepository

    ) {
        this.applicationProperties = applicationProperties;
        this.providerRepository = providerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public BaseDTO productList() {
        return BaseDTO.builder()
                .meta(MetaDTO.getInstance(applicationProperties))
                .data(productRepository.findAll())
                .build();
    }

    @Override
    public BaseDTO newProduct(ProductNewRequest productNewRequest) {

        Category category = new Category();
        category.setId(productNewRequest.getCategoryId());
        Product product = new Product();
        product.setCategory(category);
        product.setDescription(productNewRequest.getDescription());
        product.setTitle(productNewRequest.getTitle());
        product.setImage(productNewRequest.getImage());
        product.setVoteIsPublic(productNewRequest.getVoteIsPublic());
        product.setCommentIsPublic(productNewRequest.getCommentIsPublic());

        return BaseDTO.builder()
                .meta(MetaDTO.getInstance(applicationProperties))
                .data(productRepository.save(product))
                .build();
    }

    @Override
    public BaseDTO changeCommentIsPublic(ProductChangeRequest productChangeRequest) {
        Product product = this.productById(productChangeRequest.getId());
        product.setVoteIsPublic(productChangeRequest.getStatus());
        return BaseDTO.builder()
                .meta(MetaDTO.getInstance(applicationProperties))
                .data(productRepository.save(product))
                .build();
    }

    @Override
    public BaseDTO changeVoteIsPublic(ProductChangeRequest productChangeRequest) {
        Product product = this.productById(productChangeRequest.getId());
        product.setVoteIsPublic(productChangeRequest.getStatus());
        return BaseDTO.builder()
                .meta(MetaDTO.getInstance(applicationProperties))
                .data(productRepository.save(product))
                .build();
    }

    @Override
    public BaseDTO getProductById(Long id) {
        Product product = this.productById(id);
        return BaseDTO.builder()
                .meta(MetaDTO.getInstance(applicationProperties))
                .data(product)
                .build();
    }

    @Override
    public BaseDTO review(Integer pageNumber, Integer pageSize, Integer categoryId) {

        int pageNum = 1;
        int pSize = 10;
        if (pageNumber != null && pageNumber > 0) {
            pageNum = pageNumber;
        }
        if (pageSize != null && pageSize > 0) {
            pSize = pageSize;
        }

        if (categoryId == null) {
            categoryId = 0;
        }

        pageNum = pSize * (pageNum - 1);
        List<Object[]> objects = productRepository.review(pSize, pageNum, 4, categoryId);
        List<ReviewDTO> reviewResponses = new ArrayList<>();

        for (Object[] product : objects) {
            reviewResponses.add(ReviewDTO.builder()
                    .productId(Long.parseLong(product[0].toString()))
                    .isShow((Boolean) product[1])
                    .commentIsEnable((Boolean) product[2])
                    .voteIsEnable((Boolean) product[3])
                    .commentIsPublic((Boolean) product[4])
                    .voteIsPublic((Boolean) product[5])
                    .title((product[6] != null) ? (String) product[6] : "")
                    .rate((product[7] != null) ? Double.parseDouble(product[7].toString()) : 0d)
                    .count((product[8] != null) ? (Integer.parseInt(product[8].toString())) : 0)
                    .comment((product[9] != null) ? (String) product[9] : "")
                    .image((product[10] != null) ? (String) product[10] : "")
                    .build()
            );
        }
        List<ReviewProductDTO> reviewProductDTOS = new ArrayList<>();
        List<ReviewDTO> reviewDistinct = reviewResponses.stream().filter(distinctByKey(p -> p.getProductId())).collect(Collectors.toList());
        for (ReviewDTO reviewDTO : reviewDistinct) {
            List<CommentReviewDTO> commentReviewDTOS = new ArrayList<>();
            for (ReviewDTO dto : reviewResponses.stream().filter(p -> p.getProductId() == reviewDTO.getProductId()).collect(Collectors.toList())) {
                if (!dto.getComment().equalsIgnoreCase(""))
                    commentReviewDTOS.add(CommentReviewDTO.builder().comment(dto.getComment()).build());
            }
            reviewProductDTOS.add(ReviewProductDTO.builder()
                    .productId(reviewDTO.getProductId())
                    .isShow(reviewDTO.getIsShow())
                    .commentIsEnable(reviewDTO.getCommentIsEnable())
                    .voteIsEnable(reviewDTO.getVoteIsEnable())
                    .commentIsPublic(reviewDTO.getCommentIsPublic())
                    .voteIsPublic(reviewDTO.getVoteIsPublic())
                    .title(reviewDTO.getTitle())
                    .rate(reviewDTO.getRate())
                    .image(reviewDTO.getImage())
                    .commentCount(reviewDTO.getCount())
                    .comments((commentReviewDTOS.size() != 0) ? commentReviewDTOS : null).build()
            );
        }
        return BaseDTO.builder()
                .meta(MetaDTO.getInstance(applicationProperties))
                .data(reviewProductDTOS)
                .build();
    }

    Product productById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> ServiceException.builder()
                        .code(applicationProperties.getCode("application.message.notFoundRecord.code"))
                        .message(applicationProperties.getProperty("application.message.notFoundRecord.text"))
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .build()

        );
        return product;
    }
}
