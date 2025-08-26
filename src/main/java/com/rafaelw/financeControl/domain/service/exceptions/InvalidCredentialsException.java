package com.rafaelw.financeControl.domain.service.exceptions;

public class InvalidCredentialsException extends RuntimeException {

  public InvalidCredentialsException(String message) {
    super(message);
  }
}
