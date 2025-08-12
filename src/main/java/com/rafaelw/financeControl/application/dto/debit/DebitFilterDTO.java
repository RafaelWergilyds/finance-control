package com.rafaelw.financeControl.application.dto.debit;

import java.math.BigDecimal;

public record DebitFilterDTO(
    Long categoryId,
    String categoryName,
    BigDecimal maxAmount,
    BigDecimal minAmount
) {

}
