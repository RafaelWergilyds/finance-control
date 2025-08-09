package com.rafaelw.financeControl.infra.dto.debit;

import java.math.BigDecimal;

public record DebitResponseDTO(
    Long id,
    String name,
    BigDecimal amount,
    Long categoryId
) {

}
