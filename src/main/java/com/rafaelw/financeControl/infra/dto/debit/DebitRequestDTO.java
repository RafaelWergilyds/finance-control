package com.rafaelw.financeControl.infra.dto.debit;

import java.math.BigDecimal;

public record DebitRequestDTO(String name, BigDecimal amount, Long categoryId) {

}
