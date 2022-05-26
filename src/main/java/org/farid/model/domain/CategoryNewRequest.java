package org.farid.model.domain;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class CategoryNewRequest {

    @NotBlank
    String title;
    String description;
}
