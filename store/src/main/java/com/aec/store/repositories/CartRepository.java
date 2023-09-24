package com.aec.store.repositories;

import com.aec.store.models.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, String> {
}
