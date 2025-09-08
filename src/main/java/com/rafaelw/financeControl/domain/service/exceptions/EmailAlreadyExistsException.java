package com.rafaelw.financeControl.domain.service.exceptions;

public class EmailAlreadyExistsException extends InvalidCredentialsException {

  public EmailAlreadyExistsException(String email) {
    super(String.format("User with e-mail %s already exist", email));
  }
}
