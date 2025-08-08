package com.rafaelw.financeControl.domain.repository;

import com.rafaelw.financeControl.infra.db.entities.CategoryEntity;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

  List<CategoryEntity> findAllByUserId(Long id);

  Optional<CategoryEntity> findByIdAndUserId(Long categoryId, Long userId);
}
