package org.farid.model.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "category")
public class Category extends BaseEntity<Integer> {

    @Column(name = "title", length = 100, nullable = false)
    String title;

    @Column(name = "description", length = 1500, nullable = false)
    String description;

    @JsonManagedReference
    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Product> products;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
