package com.rafaelw.financeControl.infra.db.repository;

import com.rafaelw.financeControl.infra.db.entities.DebitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import com.rafaelw.financeControl.domain.repository.DebitRepository;

public interface JpaDebitRepository extends JpaRepository<DebitEntity, Long>, DebitRepository{

}
