package com.rafaelw.financeControl.infra.dto.debit;

import java.math.BigDecimal;

public record DebitResponseDTO(String name, BigDecimal amount) {

}
