package org.farid.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ReviewProductDTO {
    Long productId;
    Boolean isShow;
    Boolean commentIsEnable;
    Boolean voteIsEnable;
    Boolean commentIsPublic;
    Boolean voteIsPublic;
    String title;
    String image;
    Double rate;
    Integer commentCount;
    List<CommentReviewDTO> comments;
}
