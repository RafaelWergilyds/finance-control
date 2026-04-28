package com.rafaelw.financeControl.application.ports.in;

import com.rafaelw.financeControl.domain.model.entities.Debit;
import com.rafaelw.financeControl.application.dto.DebitFilterDTO;
import com.rafaelw.financeControl.infra.outbound.persistence.repository.utils.PaginatedResponse;

import java.math.BigDecimal;
import java.util.Optional;

public interface DebitService {
    Debit create(Long userId, String name, BigDecimal amount, Long categoryId);
    Optional<Debit> findById(Long userId, Long id);
    PaginatedResponse<Debit> findAll(Long userId, DebitFilterDTO filter, Integer pageSize, Long cursor);
    Optional<Debit> update(Long userId, Long id, String name, BigDecimal amount, Long categoryId);
    void delete(Long userid, Long id);
}
