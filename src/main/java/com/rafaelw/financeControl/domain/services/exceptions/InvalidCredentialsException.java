package com.rafaelw.financeControl.domain.services.exceptions;

public class InvalidCredentialsException extends RuntimeException {

  public InvalidCredentialsException(String message) {
    super(message);
  }
}
