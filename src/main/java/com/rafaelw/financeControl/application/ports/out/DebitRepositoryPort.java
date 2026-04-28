package com.rafaelw.financeControl.application.ports.out;

import com.rafaelw.financeControl.domain.model.entities.Debit;
import com.rafaelw.financeControl.application.dto.DebitFilterDTO;
import com.rafaelw.financeControl.infra.outbound.persistence.repository.utils.PaginatedResponse;

import java.math.BigDecimal;
import java.util.Optional;

public interface DebitRepositoryPort {
    Debit save(Debit debit);
    PaginatedResponse<Debit> findAllByUser(Long userId, DebitFilterDTO filter, Integer pageSize, Long cursor);
    Optional<Debit> findByIdAndUserId(Long id, Long userId);
    BigDecimal getTotalSumAmount(Long userId, DebitFilterDTO filter);
    void delete(Long id);

}
