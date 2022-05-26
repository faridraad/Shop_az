package org.farid.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "provider")
public class Provider extends BaseEntity<Integer> {

    @Column(name = "title", length = 100, nullable = false)
    String title;

    @Column(name = "address", length = 1500, nullable = false)
    String address;

    @Column(name = "phone_number", length = 1000, nullable = false)
    String phoneNumber;

    @JsonManagedReference
    @OneToMany(mappedBy = "provider",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<ProductProvider> productProviders ;

//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "product_provider",
//            joinColumns = {@JoinColumn(name = "provider_id")},
//            inverseJoinColumns = {@JoinColumn(name = "product_id")})
//    private List<Product> products;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<ProductProvider> getProductProviders() {
        return productProviders;
    }

    public void setProductProviders(List<ProductProvider> productProviders) {
        this.productProviders = productProviders;
    }
}
