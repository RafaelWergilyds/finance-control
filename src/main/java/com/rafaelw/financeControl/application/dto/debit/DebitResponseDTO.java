package com.rafaelw.financeControl.application.dto.debit;

import java.math.BigDecimal;
import java.time.Instant;

public record DebitResponseDTO(
    Long id,
    String name,
    BigDecimal amount,
    Instant moment,
    Long categoryId
) {

}
