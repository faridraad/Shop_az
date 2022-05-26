package org.farid.model.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentNewRequest {

    Integer memberId;
    Integer providerId;
    Long productId;
    @NotBlank
    String comment;
}
