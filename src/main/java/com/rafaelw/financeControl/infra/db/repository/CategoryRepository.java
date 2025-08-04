package com.rafaelw.financeControl.infra.db.repository;

import com.rafaelw.financeControl.infra.db.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

}
