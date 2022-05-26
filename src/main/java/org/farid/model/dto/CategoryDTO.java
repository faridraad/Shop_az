package org.farid.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CategoryDTO {
    Integer id;
    String title;
    String description;
}
