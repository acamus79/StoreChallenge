package com.aec.store.repositories;

import com.aec.store.models.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, String> {
    Optional<CartEntity> findByUserIdAndConfirmIsFalse(String userId);
    List<CartEntity> findByUserIdAndConfirmIsTrue(String userId);

}
