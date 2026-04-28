package com.rafaelw.financeControl.infra.outbound.persistence.repository;
import com.rafaelw.financeControl.infra.outbound.persistence.entities.CategoryPersist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaCategoryRepository extends JpaRepository<CategoryPersist, Long> {
    Optional<CategoryPersist> findByIdAndUserId(Long id, Long userId);
    List<CategoryPersist> findAllByUserId(Long userId);
}
