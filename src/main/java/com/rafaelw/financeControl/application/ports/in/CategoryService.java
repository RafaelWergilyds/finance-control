package com.rafaelw.financeControl.application.ports.in;

import com.rafaelw.financeControl.domain.model.entities.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category create(Long id, String name);
    Optional<Category> findById(Long userId, Long id);
    List<Category> findAll(Long userId);
    Optional<Category> update(Long userId, Long id, String name);
    void delete(Long userId, Long id);
}
