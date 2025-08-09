package com.rafaelw.financeControl.domain.service;

import com.rafaelw.financeControl.domain.entities.Debit;
import com.rafaelw.financeControl.domain.entities.User;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class UserCreateDebit {

  public Debit execute(User user, String name, BigDecimal amount) {
    return new Debit(user, name, amount);
  }

}
