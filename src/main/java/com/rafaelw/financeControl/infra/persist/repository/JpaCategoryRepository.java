package com.rafaelw.financeControl.infra.persist.repository;

import com.rafaelw.financeControl.domain.repository.CategoryRepository;
import com.rafaelw.financeControl.infra.persist.entities.CategoryPersist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCategoryRepository extends JpaRepository<CategoryPersist, Long>,
    CategoryRepository {

}
