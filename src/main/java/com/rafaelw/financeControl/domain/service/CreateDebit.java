package com.rafaelw.financeControl.domain.service;

import com.rafaelw.financeControl.domain.entities.Category;
import com.rafaelw.financeControl.domain.entities.Debit;
import com.rafaelw.financeControl.domain.entities.User;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class CreateDebit {

  public Debit createDebit(User user, Category category, String name, BigDecimal amount){
    Debit debit = new Debit();
    debit.setUser(user);
    debit.setCategory(category);
    debit.setName(name);
    debit.setAmount(amount);

    return debit;
  }

}
