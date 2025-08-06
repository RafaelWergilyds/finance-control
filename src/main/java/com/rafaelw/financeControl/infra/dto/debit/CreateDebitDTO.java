package com.rafaelw.financeControl.infra.dto.debit;

import java.math.BigDecimal;

public record CreateDebitDTO (Long userId, Long categoryId, String name, BigDecimal amount){

}
