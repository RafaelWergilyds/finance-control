package com.rafaelw.financeControl.domain.factories;

import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.domain.service.VerifyUserByEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {

  @Autowired
  private VerifyUserByEmail verifyUserByEmail;

  public User create(String name, String email, String password) {
    verifyUserByEmail.execute(email);
    return User.create(name, email, password);
  }
}
