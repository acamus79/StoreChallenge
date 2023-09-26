package com.aec.store.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BasicService<T, ID> {

    T save(T t);

    Optional<T> findById(ID id);

    List<T> findAll();

    Page<T> findAll(Pageable pageable);

    T edit(T t);

    void delete(T t);

    void deleteById(ID id);

    boolean existById(ID id);
}
