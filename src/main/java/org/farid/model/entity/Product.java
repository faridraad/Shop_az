package org.farid.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "product")
public class Product extends BaseEntity<Long> {

    @Column(name = "title", length = 100, nullable = false)
    String title;

    @Column(name = "image", length = 4000, nullable = false)
    String image;

    @Column(name = "description", length = 2000, nullable = false)
    String description;

    @Column(name = "is_show", nullable = false)
    Boolean isShow = true;

    @Column(name = "vote_is_public", nullable = false)
    Boolean voteIsPublic = true;

    @Column(name = "comment_is_public", nullable = false)
    Boolean commentIsPublic = true;

    @Column(name = "vote_is_enable", nullable = false)
    Boolean voteIsEnable = true;

    @Column(name = "comment_is_enable", nullable = false)
    Boolean commentIsEnable = true;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    Category category;

    @JsonManagedReference
    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductProvider> productProviders ;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getShow() {
        return isShow;
    }

    public void setShow(Boolean show) {
        isShow = show;
    }

    public Boolean getVoteIsPublic() {
        return voteIsPublic;
    }

    public void setVoteIsPublic(Boolean voteIsPublic) {
        this.voteIsPublic = voteIsPublic;
    }

    public Boolean getCommentIsPublic() {
        return commentIsPublic;
    }

    public void setCommentIsPublic(Boolean commentIsPublic) {
        this.commentIsPublic = commentIsPublic;
    }

    public Boolean getVoteIsEnable() {
        return voteIsEnable;
    }

    public void setVoteIsEnable(Boolean voteIsEnable) {
        this.voteIsEnable = voteIsEnable;
    }

    public Boolean getCommentIsEnable() {
        return commentIsEnable;
    }

    public void setCommentIsEnable(Boolean commentIsEnable) {
        this.commentIsEnable = commentIsEnable;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<ProductProvider> getProductProviders() {
        return productProviders;
    }

    public void setProductProviders(List<ProductProvider> productProviders) {
        this.productProviders = productProviders;
    }
}
