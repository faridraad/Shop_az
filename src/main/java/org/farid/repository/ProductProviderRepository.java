package org.farid.repository;

import org.farid.model.entity.ProductProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface ProductProviderRepository extends JpaRepository<ProductProvider, Long> {
    List<ProductProvider> findAll();
    Optional<ProductProvider> findById(Integer id);
    List<ProductProvider> findByProductIdIn(List<Long>  productIdList);
}
