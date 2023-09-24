package com.aec.store.repositories;

import com.aec.store.models.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    boolean existsByName(String name);
}
