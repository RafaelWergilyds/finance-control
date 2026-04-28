package com.rafaelw.financeControl.application.service.exceptions;

import com.rafaelw.financeControl.domain.services.exceptions.InvalidCredentialsException;

public class EmailNotRegisteredException extends InvalidCredentialsException {

  public EmailNotRegisteredException() {
    super("Email Not Registered");
  }
}
