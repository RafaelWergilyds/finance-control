package com.rafaelw.financeControl.infra.persistence.repository;

import com.rafaelw.financeControl.domain.repository.CategoryRepository;
import com.rafaelw.financeControl.infra.persistence.entities.CategoryPersist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCategoryRepository extends JpaRepository<CategoryPersist, Long>,
    CategoryRepository {

}
