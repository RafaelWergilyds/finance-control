package com.rafaelw.financeControl.domain.service.exceptions;

public class UserAlreadyExistsException extends InvalidCredentials {

  public UserAlreadyExistsException(String email) {
    super(String.format("User with e-mail %s already exist", email));
  }
}
