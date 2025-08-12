package com.rafaelw.financeControl.application.services.exceptions;

public class UserNotFoundException extends NotFoundException {

  public UserNotFoundException(Long id) {
    super(String.format("User with id %s not found", id));
  }
}
