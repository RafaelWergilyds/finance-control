package com.rafaelw.financeControl.domain.service.exceptions;

public class InvalidCredentials extends RuntimeException {

  public InvalidCredentials(String message) {
    super(message);
  }
}
