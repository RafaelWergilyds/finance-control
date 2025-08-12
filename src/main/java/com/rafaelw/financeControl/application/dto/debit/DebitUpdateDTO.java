package com.rafaelw.financeControl.application.dto.debit;

import java.math.BigDecimal;

public record DebitUpdateDTO(String name, BigDecimal amount, Long categoryId) {

}
