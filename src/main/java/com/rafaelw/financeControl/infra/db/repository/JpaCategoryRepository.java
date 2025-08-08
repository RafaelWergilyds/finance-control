package com.rafaelw.financeControl.infra.db.repository;

import com.rafaelw.financeControl.domain.repository.CategoryRepository;
import com.rafaelw.financeControl.infra.db.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCategoryRepository extends JpaRepository<CategoryEntity, Long>,
    CategoryRepository {

}
