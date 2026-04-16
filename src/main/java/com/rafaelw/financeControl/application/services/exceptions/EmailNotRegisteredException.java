package com.rafaelw.financeControl.application.services.exceptions;

import com.rafaelw.financeControl.domain.services.exceptions.InvalidCredentialsException;

public class EmailNotRegisteredException extends InvalidCredentialsException {

  public EmailNotRegisteredException() {
    super("Email Not Registered");
  }
}
