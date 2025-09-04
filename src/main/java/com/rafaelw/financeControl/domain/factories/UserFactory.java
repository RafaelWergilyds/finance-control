package com.rafaelw.financeControl.domain.factories;

import com.rafaelw.financeControl.domain.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {

  public User create(String name, String email, String password) {
    return User.create(name, email, password);
  }
}
