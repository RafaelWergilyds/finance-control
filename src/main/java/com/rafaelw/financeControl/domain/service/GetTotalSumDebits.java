package com.rafaelw.financeControl.domain.service;

import com.rafaelw.financeControl.domain.entities.Debit;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetTotalSumDebits {

  public BigDecimal execute(List<Debit> debits) {
    if (debits == null || debits.isEmpty()) {
      return BigDecimal.ZERO;
    }
    return debits.stream().map(Debit::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
  }

}
