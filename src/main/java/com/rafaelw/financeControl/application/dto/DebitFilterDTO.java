package com.rafaelw.financeControl.application.dto;

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
