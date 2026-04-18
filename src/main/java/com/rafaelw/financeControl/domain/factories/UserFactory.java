package com.rafaelw.financeControl.domain.factories;

import com.rafaelw.financeControl.domain.entities.User;
import com.rafaelw.financeControl.domain.valueObjects.Email;
import com.rafaelw.financeControl.domain.valueObjects.Password;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {

  public static User create(String name, String email, String rawPassword, PasswordEncoder encoder) {
    return new User(
            name,
            new Email(email),
            Password.create(rawPassword, encoder));
  }
}
