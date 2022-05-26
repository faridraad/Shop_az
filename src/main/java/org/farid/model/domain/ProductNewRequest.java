package org.farid.model.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProductNewRequest {

    Integer categoryId;
    @NotBlank
    String title;
    String  description;
        String image;
    Boolean voteIsPublic = true;
    Boolean commentIsPublic = true;

}
