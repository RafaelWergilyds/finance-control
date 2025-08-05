package com.rafaelw.financeControl.infra.services.exceptions;

public class UserAlreadyExistsException extends InvalidCredentials {

  public UserAlreadyExistsException(String email) {
    super(String.format("User with e-mail %s already exist", email));
  }
}
