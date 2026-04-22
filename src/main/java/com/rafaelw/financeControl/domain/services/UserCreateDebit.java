package com.rafaelw.financeControl.domain.services;

import com.rafaelw.financeControl.domain.model.entities.Debit;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class UserCreateDebit {

  public Debit execute(Long userId, String name, BigDecimal amount) {
    return new Debit(userId, name, amount);
  }

}
