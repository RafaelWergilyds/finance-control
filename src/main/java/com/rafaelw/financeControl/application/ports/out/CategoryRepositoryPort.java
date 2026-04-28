package com.rafaelw.financeControl.application.ports.out;

import com.rafaelw.financeControl.domain.model.entities.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryPort {
    Category save(Category category);
    List<Category> findAllByUserId(Long userId);
    Optional<Category> findByIdAndUserId( Long id, Long userId);
    void delete(Long id);
}
