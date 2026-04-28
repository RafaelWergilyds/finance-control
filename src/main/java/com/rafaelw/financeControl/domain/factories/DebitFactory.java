package com.rafaelw.financeControl.domain.factories;

import com.rafaelw.financeControl.domain.model.entities.Debit;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class DebitFactory {

  public static Debit create(Long userId, String name, BigDecimal amount) {
    return new Debit(userId, name, amount);
  }
}
