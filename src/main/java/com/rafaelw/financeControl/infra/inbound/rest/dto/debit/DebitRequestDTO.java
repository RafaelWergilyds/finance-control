package com.rafaelw.financeControl.infra.inbound.rest.dto.debit;

import java.math.BigDecimal;

public record DebitRequestDTO(String name, BigDecimal amount, Long categoryId) {

}
