package com.rafaelw.financeControl.domain.repository;

import com.rafaelw.financeControl.infra.persist.entities.CategoryPersist;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

  List<CategoryPersist> findAllByUserId(Long id);

  Optional<CategoryPersist> findByIdAndUserId(Long categoryId, Long userId);
}
