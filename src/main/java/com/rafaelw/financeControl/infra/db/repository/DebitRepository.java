package com.rafaelw.financeControl.infra.db.repository;

import com.rafaelw.financeControl.infra.db.entities.DebitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebitRepository extends JpaRepository<DebitEntity, Long> {

}
