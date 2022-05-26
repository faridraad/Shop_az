package org.farid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.farid.model.entity.Provider;
import java.util.List;
import java.util.Optional;


@Repository
public interface ProviderRepository extends JpaRepository<Provider, Integer> {
    List<Provider> findAll();
    Optional<Provider> findById(Integer id);
}
