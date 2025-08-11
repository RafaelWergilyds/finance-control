package com.rafaelw.financeControl.infra.dto.debit;

import java.math.BigDecimal;

public record DebitUpdateDTO(String name, BigDecimal amount, Long categoryId) {

}
