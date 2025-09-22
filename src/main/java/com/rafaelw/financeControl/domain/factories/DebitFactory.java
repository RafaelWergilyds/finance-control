package com.rafaelw.financeControl.domain.factories;

import com.rafaelw.financeControl.domain.entities.Debit;
import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.domain.services.UserCreateDebit;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DebitFactory {

  @Autowired
  private UserCreateDebit userCreateDebit;

  public Debit create(User user, String name, BigDecimal amount) {
    return userCreateDebit.execute(user, name, amount);
  }
}
