package com.rafaelw.financeControl.domain.repository;

import com.rafaelw.financeControl.infra.persistence.entities.DebitPersist;
import java.util.List;
import java.util.Optional;

public interface DebitRepository {

  Optional<DebitPersist> findByIdAndUserId(Long debitId, Long userId);

  List<DebitPersist> findAllByCategoryId(Long id);
}
