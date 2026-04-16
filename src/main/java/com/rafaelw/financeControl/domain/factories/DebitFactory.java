package com.rafaelw.financeControl.domain.factories;

import com.rafaelw.financeControl.domain.entities.Debit;
import com.rafaelw.financeControl.domain.entities.User;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class DebitFactory {

  public Debit create(Long userId, String name, BigDecimal amount) {
    return new Debit(userId, name, amount);
  }
}
