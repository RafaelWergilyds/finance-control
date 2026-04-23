package com.rafaelw.financeControl.infra.inbound.rest.dto.debit;

import java.math.BigDecimal;

public record DebitUpdateDTO(String name, BigDecimal amount, Long categoryId) {

}
