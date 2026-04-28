package com.rafaelw.financeControl.infra.inbound.rest.dto.debit;

import java.math.BigDecimal;

public record TotalDebitsResponse(BigDecimal total) {
    public static TotalDebitsResponse total(BigDecimal total){
        return new TotalDebitsResponse(total);
    }

}
