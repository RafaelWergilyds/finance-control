package com.rafaelw.financeControl.infra.inbound.rest.dto.debit;

import com.rafaelw.financeControl.domain.model.entities.Debit;

import java.math.BigDecimal;
import java.time.Instant;

public record DebitResponseDTO(
    Long id,
    String name,
    BigDecimal amount,
    Instant moment,
    Long categoryId
) {

    public static DebitResponseDTO fromDomain(Debit debit){
        return new DebitResponseDTO(debit.getId(), debit.getName(), debit.getAmount(), debit.getMoment(), debit.getCategoryId());
    }

}
