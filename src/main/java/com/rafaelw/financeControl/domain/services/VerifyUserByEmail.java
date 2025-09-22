package com.rafaelw.financeControl.domain.services;

import com.rafaelw.financeControl.domain.repository.UserRepository;
import com.rafaelw.financeControl.domain.services.exceptions.EmailAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifyUserByEmail {

  @Autowired
  private UserRepository userRepository;

  public void execute(String email) {
    if (userRepository.existsByEmail(email)) {
      throw new EmailAlreadyExistsException(email);
    }
  }
}
