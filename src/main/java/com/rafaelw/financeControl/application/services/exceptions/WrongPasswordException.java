package com.rafaelw.financeControl.application.services.exceptions;

import com.rafaelw.financeControl.domain.service.exceptions.InvalidCredentialsException;

public class WrongPasswordException extends InvalidCredentialsException {

  public WrongPasswordException() {
    super("Wrong Password");
  }
}
