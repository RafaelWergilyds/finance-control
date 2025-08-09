package com.rafaelw.financeControl.domain.repository;

import com.rafaelw.financeControl.infra.db.entities.DebitEntity;
import java.util.List;
import java.util.Optional;

public interface DebitRepository {

  List<DebitEntity> findAllByUserId(Long id);

  Optional<DebitEntity> findByIdAndUserId(Long debitId, Long userId);

  List<DebitEntity> findAllByCategoryId(Long id);
}
