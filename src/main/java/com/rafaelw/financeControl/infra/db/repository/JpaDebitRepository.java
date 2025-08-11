package com.rafaelw.financeControl.infra.db.repository;

import com.rafaelw.financeControl.domain.repository.DebitRepository;
import com.rafaelw.financeControl.infra.db.entities.DebitPersist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDebitRepository extends JpaRepository<DebitPersist, Long>, DebitRepository {

}
