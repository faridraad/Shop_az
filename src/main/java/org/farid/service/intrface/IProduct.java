package org.farid.service.intrface;

import org.farid.model.domain.ProductChangeRequest;
import org.farid.model.domain.ProductNewRequest;
import org.farid.model.dto.BaseDTO;

public interface IProduct {

        BaseDTO productList ();
        BaseDTO newProduct(ProductNewRequest productNewRequest);
        BaseDTO changeCommentIsPublic(ProductChangeRequest productChangeRequest);
        BaseDTO changeVoteIsPublic(ProductChangeRequest productChangeRequest);
        BaseDTO getProductById(Long id);
        BaseDTO review(Integer pageNumber, Integer pageSize ,Integer categoryId);

}
