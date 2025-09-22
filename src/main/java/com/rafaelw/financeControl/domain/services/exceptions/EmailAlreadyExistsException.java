package com.rafaelw.financeControl.domain.services.exceptions;

public class EmailAlreadyExistsException extends InvalidCredentialsException {

  public EmailAlreadyExistsException(String email) {
    super(String.format("User with e-mail %s already exist", email));
  }
}
