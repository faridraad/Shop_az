package org.farid.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;


@Entity
@Table(name = "vote" , uniqueConstraints={@UniqueConstraint(name = "unique_vote",columnNames = {"product_id", "member_id","provider_id"})})
public class Vote extends BaseEntity<Integer>{

    @Column(name = "rate")
    Integer rate;

    @Column(name = "is_confirm", nullable = false)
    Boolean isConfirm = false;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_id")
    Product product;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "member_id")
    Member member;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "provider_id")
    Provider provider;

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Boolean getConfirm() {
        return isConfirm;
    }

    public void setConfirm(Boolean confirm) {
        isConfirm = confirm;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}
