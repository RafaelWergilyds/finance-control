package com.rafaelw.financeControl.application.dto.debit;

import java.math.BigDecimal;

public record DebitRequestDTO(String name, BigDecimal amount, Long categoryId) {

}
