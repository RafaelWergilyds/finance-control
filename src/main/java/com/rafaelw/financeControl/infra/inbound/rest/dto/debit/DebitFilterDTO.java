package com.rafaelw.financeControl.infra.inbound.rest.dto.debit;

import java.math.BigDecimal;

public record DebitFilterDTO(
    Long categoryId,
    String categoryName,
    BigDecimal maxAmount,
    BigDecimal minAmount,
    String since,
    String until
) {

}
