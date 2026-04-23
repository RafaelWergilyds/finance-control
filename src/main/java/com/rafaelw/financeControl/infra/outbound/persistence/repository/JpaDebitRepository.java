package com.rafaelw.financeControl.infra.outbound.persistence.repository;

import com.rafaelw.financeControl.domain.repository.DebitRepository;
import com.rafaelw.financeControl.infra.outbound.persistence.entities.DebitPersist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaDebitRepository extends JpaRepository<DebitPersist, Long>, DebitRepository,
    JpaSpecificationExecutor<DebitPersist> {
}
