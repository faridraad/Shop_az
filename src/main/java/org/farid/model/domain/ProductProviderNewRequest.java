package org.farid.model.domain;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class ProductProviderNewRequest {

    Long    productId;
    Integer providerId;

    @NotBlank
    Float price;

    @NotBlank
    String title;

    String image;
    Boolean voteIsPublic = true;
    Boolean commentIsPublic = true;

}
