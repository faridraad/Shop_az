package org.farid.model.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProductChangeRequest {

    Long id;
    Boolean status = true;

}
