package com.rafaelw.financeControl.infra.db.repository;

import com.rafaelw.financeControl.domain.repository.CategoryRepository;
import com.rafaelw.financeControl.infra.db.entities.CategoryPersist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCategoryRepository extends JpaRepository<CategoryPersist, Long>,
    CategoryRepository {

}
