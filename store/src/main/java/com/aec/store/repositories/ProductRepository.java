package com.aec.store.repositories;

import com.aec.store.models.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    boolean existsByNameAndSoftDeleteFalse(String name);
    List<ProductEntity> findAllByIdIn(List<String> productIds);
}
