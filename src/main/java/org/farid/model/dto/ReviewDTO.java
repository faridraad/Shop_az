package org.farid.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReviewDTO {
    Long productId;
    Boolean isShow;
    Boolean commentIsEnable;
    Boolean voteIsEnable;
    Boolean commentIsPublic;
    Boolean voteIsPublic;
    String title;
    String image;
    Double rate;
    Integer count;
    String comment;
}
